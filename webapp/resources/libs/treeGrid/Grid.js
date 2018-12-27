function GridInit(layout, target, data){
    require(['jquery'], function($) {
    	if(layout != null && layout.length > 0){
	        // -----------------------------------------------------------------------------------------
	        // Grid init
	        var gridLayout = {
	            Cfg: {
	                //id:"Json",
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
	                RelHeight: 1,
	                Paging:3
	            },
	            Pager: { Visible: 0 },     <!-- displays visible row position -->
	            Toolbar: {
	                Formula: '"전체:"+count(7)+", 조회:"+count(6)+", 신규:"+count("Row.Added==1",7)+", 삭제:"+count("Row.Deleted>0",7)+", 수정:"+count("Row.Changed==1",7)',
	                Indent: "0",
	                Outdent: "0"
	            }
	        };
	        
	        var headerObj = {};
	        var cols = [];
	        $(layout).each(function(idx, item) {
	            if (item.TREE_YN = "Y") {
	            	gridLayout.Cfg.MainCol = item.name;
	            }
	            
	            headerObj[item.Name] = item.Header;
	            headerObj["Align"] = "Center";
	            
	            var col = {
            		Name: item.Name,
            		Width: item.Width,
            		Type: item.Type,
            		Align: item.Align
	            }
	            
	            if(item.EmptyValue){
	            	col.EmptyValue = item.EmptyValue;
	            }
	            
	            if(item.Default){
	            	col.Defaults = item.Defaults;
	            }
	            
	            if(item.CanEdit){
	            	col.CanEdit = item.CanEdit;
	            }
	            
	            if(item.CanSort){
	            	col.CanSort = item.CanSort;
	            }
	            
	            if(item.RelWidth){
	            	col.RelWidth = item.RelWidth;
	            }
	            
	            if(item.Format){
	            	col.Format = item.Format;
	            }
	            cols.push(col);
	        });
	        gridLayout.Cols = cols;
	        gridLayout.Cfg.id = target;
	        gridLayout.Header = headerObj;
	
	        //console.log("gridLayout=="+JSON.stringify(gridLayout));
	        //console.log("gridData=="+JSON.stringify(gridData));
	        var gridData = {};
	        if(data){
		        var gridBody = [];
		            gridBody.push(data);
		            gridData["Body"] = gridBody;
	        }
	        TreeGrid( 
	        	{
	        		Layout: { Data: gridLayout },
		        	Data: { Data: gridData }, 
		        	Debug:"", 
		        	Sync:"1", 
	        		Upload_Type: "changes, Body"
                }, 
                target 
            );
	        $(target+" .TSToolbarRow .TSSpaceWidthInner").html("");
    		Grids[target].DateStrings = 'yyyyMMdd';
    		Grids[target].Source.Upload.Format = "JSON";
	        if(!gridData){
	        	$(target+" .TSNoDataRow .TSSpaceWidthInner").attr("style", $(target+" .TSNoDataRow .TSSpaceWidthInner").attr("style")+" text-align:center;height:100px;vertical-align:middle");
	        }
	
	        // -----------------------------------------------------------------------------------------
	        // Custom event for searching
	        // Searches in orders with specified items
	        Grids[target].OnRowSearch = function(G,row,col,found,F,type){
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
	        Grids[target].OnGetMenu = function(G,row,col){
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
	        Grids[target].OnContextMenu = function(G,row,col,N){
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
	        Grids[target].OnLimitScroll = function(G,clr,noh,nov){
	            G.SetAttribute(G.Rows.Group,"Win","CanEdit",clr,1);
	            var tip = clr?"Disable grid scrollbars and use page scrollbars":"Used page scrollbars because browser window is too small to show this example with its own scrollbars";
	            G.SetAttribute(G.Rows.Group,"Win","Tip",tip,1);
	            G.SetAttribute(G.Rows.Group,"WinLabelRight","Tip",tip,1);
	        }
	        // -----------------------------------------------------------------------------------------
        }
    });
}

function GridSearch(target, param){
	require(['jquery'], function($) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/doExcute",
            data: JSON.stringify(param),
            dataType: 'json',
            timeout: 100000,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", $("#csrf").val());
            },
            success: function (data) {
                if(data.resultData.ds_result.length){
                	console.log("gridData Load OK: "+target);
                    var gridData = [];
                    gridData[0] = data.resultData.ds_result;
                    Grids[target].Source.Data.Data.Body = gridData;
                } else {
                	console.log("gridData No Data: "+target);
                    Grids[target].Source.Data.Data = [];
                }
                Grids[target].ReloadBody();
            },
            error: function (e) {
            	//console.log("ERROR: ", e);
	            var msg = '조회 중 오류가 발생하였습니다! 시스템 운영자에게 문의 바랍니다.';
	            var title = '';
	            toastr['info'](msg, title, {positionClass: 'toast-bottom-full-width'});
            },
            done: function (e) {
                console.log("DONE");
            }
        });
    });
}
