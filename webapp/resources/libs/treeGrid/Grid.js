function GridInit(layout, target, data){
    require(['jquery'], function($) {
        // -----------------------------------------------------------------------------------------
        // Grid init
        var layoutSetting = {
            Cfg: {
                id:"Json",
                //MainCol:"C1",          <!-- Shows tree in column -->
                //Sort:"C1",             <!-- Grid is sorted according to column A by default -->
                ResizingMain:"3",      <!-- User can resize grid main tag in both directions -->
                ExportType:"Expanded, Outline", <!-- Export setting, all rows will be exported expanded and will be used Excel outline -->
                SuppressCfg:"1",       <!-- Configuration is not saved to cookies -->
                ConstHeight:"1",       <!-- Grid will always fill its main tag -->
                ReloadChanged:"1",     <!-- Grid can reload and automatically discard pending changes -->
                Undo:"1",              <!-- Undo / redo is supported, Ctrl+Z / Ctrl+Y -->
                ChildParts:"0",        <!-- Rendering children on background is disabled -->
                SuppressMessage:"2",   <!-- Grid produces no informational message -->
                CalculateSelected:"1", <!-- Recalculates rows after selection changed -->
                LimitScroll:"20",      <!-- Responsive design, for small windows sets NoVScroll and NoHScroll -->
                RowIndex:"No",         <!-- Adds row index column named 'No' -->
                RowIndexWidth:"45",    <!-- displays visible row position -->
                FullId:"1",            <!-- Tree ids contain parent's id  -->
                UseButton: "1",
                ShowButtons: "1",
                IdPrefix: "R",
                IdChars: "0123456789",
                Style:"Standard",
                CSS:"*Styles/StandardGrid.css",
                Paging:3
            },
            Pager: { Visible: 0 },     <!-- displays visible row position -->
            Cols: [
                { Name: "LOGIN_ID", Width: "120", Type: "Text", Align:"Left", CanEdit: "1", CanSort: '1' },
                { Name: "USER_NM", MinWidth: "150", RelWidth:'100', Type: "Text", Align:"Left" },
                { Name: "USER_ID", Width: "120", Type: "Text", Align:"Left" },
                { Name: "STA_YMD", Width: "120", Type: "Date", Align:"Center", Format: "yyyy.MM.dd" },
                { Name: "END_YMD", Width: "120", Type: "Date", Align:"Center", Format: "yyyy.MM.dd" },
                { Name: "PASSWORD", Width: "150", Type: "Text", Align:"Left" },
                { Name: "PWD_U_DATE", Width: "120", Type: "Date", Align:"Center", Format: "yyyy.MM.dd" },
                { Name: "ENABLED", Width: "120", Type: "Bool", Align:"Center" },
                { Name: "LOGIN_DATE", Width: "120", Type: "Date", Align:"Center", Format: "yyyy.MM.dd" },
                { Name: "LOGIN_IP", Width: "160", Type: "Text", Align:"Center" },
                { Name: "ERR_CNT", Width: "90", Type: "Int", Align:"Center" },
                { Name: "LOCK_YN", Width: "100", Type: "Bool", Align:"Center" },
                { Name: "LOCK_DATE", Width: "120", Type: "Date", Align:"Center", Format: "yyyy.MM.dd" },
                { Name: "SA_YN", Width: "100", Type: "Bool", Align:"Center" },
                { Name: "NOTE", Width: "300", Type: "Text", Align:"Center" }
            ],
            Header: {
                LOGIN_ID: "아이디", Align:"Center",
                USER_NM: "계정명", Align:"Center",
                USER_ID: "연결계정", Align:"Center",
                STA_YMD: "시작일", Align:"Center",
                END_YMD: "종료일", Align:"Center",
                PASSWORD: "비밀번호", Align:"Center",
                PWD_U_DATE: "비번변경일", Align:"Center",
                ENABLED: "사용여부", Align:"Center",
                LOGIN_DATE: "최종로그인", Align:"Center",
                LOGIN_IP: "로그인IP", Align:"Center",
                ERR_CNT: "오류횟수", Align:"Center",
                LOCK_YN: "잠김여부", Align:"Center",
                LOCK_DATE: "잠김일자", Align:"Center",
                SA_YN: "관리자여부", Align:"Center",
                NOTE: "비고", Align:"Center"
            },
            Toolbar: {
                Formula: '"전체:"+count(7)+", 조회:"+count(6)+", 신규:"+count("Row.Added==1",7)+", 삭제:"+count("Row.Deleted>0",7)+", 수정:"+count("Row.Changed==1",7)',
                Indent: "0",
                Outdent: "0"
            }
        };

        TreeGrid( {Layout:{ Data: layout }, Data:{ Data: data }, Debug:"", Sync:"1"}, target );
        $(target+" .TSToolbarRow .TSSpaceWidthInner").html("");

        // -----------------------------------------------------------------------------------------
        // Custom event for searching
        // Searches in orders with specified items
        Grids.OnRowSearch = function(G,row,col,found,F,type){
            if(type || G.SearchDefs!="") return found; // Only for "in orders with items" and default call
            if(row.Def.Name=="Data") return -1;        // Only for orders
            for(var r=row.firstChild;r;r=r.nextSibling) {
                var found = F(r,col,1);                 // calls F(r,col,type=1)
                if(!(found<=0)) return found;
            }
            return 0;
        }
        // -----------------------------------------------------------------------------------------
        // Creates popup right click menu to manipulate the row
        Grids.OnGetMenu = function(G,row,col){
            if(row.Fixed) return null;
            var I = [], M = {Items:I};
            I[I.length] = {Name:"Del",Text:row.Deleted?"삭제취소":"삭제"};
            I[I.length] = {Name:"Sel",Text:row.Selected?"선택취소":"선택"};
            I[I.length] = {Name:"Cpy",Text:"복사"};
            if(row.firstChild) I[I.length] = {Name:"CpyTree",Text:"트리복사"};
            I[I.length] = {Name:"Add",Text:"행추가"};
            if(row.firstChild) I[I.length] = {Name:"Expand",Text:row.Expanded?"Collapse children":"Expand children"};
            if(row.firstChild) I[I.length] = {Name:"Check",Text:Get(row,'X')?"Uncheck Used":"Check Used"};
            return M;
        }
        // -----------------------------------------------------------------------------------------
        // Called after selecting item in the popup right click menu
        Grids.OnContextMenu = function(G,row,col,N){
            switch(N){
                case "Del" : G.DeleteRow(row); break;
                case "Sel" : G.SelectRow(row); break;
                case "Cpy" : G.CopyRow(row,null,row); break;
                case "CpyTree" : G.CopyRows([row],null,row,1); break;
                case "Add" : G.AddRow(null,row,1); break;
                case "Expand" : if(row.Expanded) G.Collapse(row); else G.Expand(row); break;
                case "Check" : G.SetValue(row,"X",!Get(row,'X'),1); break;
            }
        }
        // -----------------------------------------------------------------------------------------
        // Called when NoVScroll / NoHScroll is set automatically due small window size, disables the checkbox for manual change of NoVScroll / NoHScroll
        Grids.OnLimitScroll = function(G,clr,noh,nov){
            G.SetAttribute(G.Rows.Group,"Win","CanEdit",clr,1);
            var tip = clr?"Disable grid scrollbars and use page scrollbars":"Used page scrollbars because browser window is too small to show this example with its own scrollbars";
            G.SetAttribute(G.Rows.Group,"Win","Tip",tip,1);
            G.SetAttribute(G.Rows.Group,"WinLabelRight","Tip",tip,1);
        }
        // -----------------------------------------------------------------------------------------
    }
}
