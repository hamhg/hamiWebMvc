/***************************************************************************
 * Program ID    : validateUtils.js
 * Program Name  : Util 관련 함수
 * Author        : HHG
 * Created Date  : 2018.07.17
 *
 * =========================================================================
 * 수정일자     수정자    내    용
 * =========================================================================
 * 2017.05.17   나종식    최초작성
 **************************************************************************/

/***************************************************************************
 * gfn_isNull	      		: Null 체크
 * gfn_isNum	        	: 문자열이 숫자형식에 맞는지 여부 체크
 * gfn_isDigit	      		: 숫자 정합성 체크
 * gfn_isFunction	  		: 화면에 함수 존재여부 체크
 * gfn_dsPrn	        	: LogView에 데이터셋의 구조, 값을 확인한다.(개발자용)
 * gfn_getRandom	    	: 요청한 자리수만큼의 랜덤값 생성 리턴 
 * gfn_isCheckDs	    	: 해당 데이터셋명으로 검색하여 데이터셋 존재여부 리턴(true/false)
 * gfn_getDataset	    	: 해당 데이터셋명으로 검색하여 데이터셋이 없으면 데이터셋을 생성
 * gfn_dsCopy	    	    : 지정한 데이터셋을 특정 Row Type만 필터링하여 복사
 * gfn_dsToParam	    	: 지정한 데이터셋을 파라미터로 만든다.
 * gfn_isRsrNo	    	    : 주민 등록번호 확인
 * gfn_isCompRegNo	    	: 사업자 등록번호 확인
 * gfn_isCorpRegNo	    	: 법인 등록번호 확인
 * gfn_getGender	    	: 주민번호로 성별을 Return 한다.
 * gfn_getBirthYear	    	: 주민번호 뒷 첫번째 자리로 년대를 return 한다
 * gfn_getAge	    	    : 생년월일 또는 주민 번호로 법적연령-만나이를 구한다.
 * gfn_isEmail	    	    : 이메일 주소 체크
 * gfn_checkSpecialChar	    : 특수문자가 있는지 체크, 영자, 한글, 숫자, 간격 문자 이외의 문자로 임의 지정
 * gfn_isHandPhone	    	: 핸드폰 체크
 * gfn_isPhone	    	    : 일반전화번호 체크
 * gfn_telSplit	    	    : 전화번호 하이픈(-)추가, 포커스 아웃에서 호출 (onkillfocus)
 * gfn_telJoin	    	    : 전화번호 하이픈(-)제거, 포커스 얻을때 사용 (onsetfocus)
 * gfn_nvl   	    	    : 입력값이 null에 해당되면 val2(대체문자열)로 반환한다.
 * gfn_isForeignerNo        : 외국인 등록번호 여부를 확인한다.
 * gfn_jsonObjectToString   : JSON Object를 JSON String으로 변환하여 리턴한다.
 * gfn_jsonStringToObject   : JSON String을 JSON Object으로 변환하여 리턴한다.
 * gfn_addSlash             : value값에 슬레쉬값을 add하여 리턴
 * gfn_round                : 주어진 수식을 반올림하여 가장 가까운 정수 또는 고정 소수점 실수를 반환 
 * gfn_getUniqArr           : Array에 있는 값들을 Distinct(중복제거)한다.
 * gfn_findArray            : 1차원 배열에서 입력된 문자열이 있는 index를 반환
 * gfn_findArray2           : 2차원 배열에서 입력된 문자열이 있는 index를 반환
 * gfn_getDatasetInfo       : Dataset의 컬럼정보를 얻는다.
 * gfn_getObjType           : Object의 type을 문자열로 얻어온다.
 * gfn_findData             : dataSet object에서 키에 해당되는 Row를 찾아서 rowpostion 전달
 * gfn_deleteData           : dataset object에서 키에 해당되는 Row를 찾아서 삭제
 * gfn_moveData             : dataset object에서 키에 해당되는 Row를 찾아서 이동
 * gfn_editData             : dataset object에서 키에 해당되는 Row를 찾아서 값을 변경
 * gfn_allRowDataCall       : Dataset의 모든 행을 함수의 인자로 넘겨주고 함수 호출처리
 * serializeObject          : FORM 을 JSON 으로 반환
 * gfn_isUpdateDs           : dataSet의 Row 중에서 변경된 내용이 있는지 여부
 * gfn_isUpdatedRow         : dataSet의 Row가 변경되었는지 판단하는 함수
 * gfn_isUpdateColumn       : dataSet의 Row 에서 해당 칼럼이 변경되었는지 판단하는 함수
 * gfn_dsGetUpdated         : value를 Dataset에 적용 후 Datset의 갱신여부를  Return 한다.
 * gfn_updateToDatasetAll   : 콤포넌트의 변경된 값을 데이타셋에 반영(탭 변경시등 사용)
 * gfn_setComboValue        : combo dataset에 data를 추가
 * gfn_makePhoneCombo       : 전화번호 앞자리 콤보박스를 만든다.
 * gfn_makeMobileCombo      : 휴대폰 앞자리 콤보박스를 만든다.
 * gfn_makeFaxCombo         : 팩스 앞자리 콤보박스를 만든다.
 * gfn_makeEmailCombo       : 이메일 콤보박스를 만든다.
 * gfn_makeNumberCombo      : 숫자 콤보박스를 만든다.
 * gfn_getCmnCode			: FO 서비스를 통해서 공통코드 조회 하기 (리턴:Data)
 * gfn_getCommonCode		: FO 서비스를 통해서 공통코드 조회 하기 (리턴:콜백)
 * gfn_getCd        		: 코드Arr에서 코드/코드명 반환
 * cmnAlert     			: Alert popup
 * cmnConfirm     			: Confirm popup
 * cmnPopup     			: common popup
 * submit2                  : form 생성하여 submit
 * submitJson               : form 생성하여 submit, param을 JSON(ds:{})으로 전달
 * submitExDn               : form 생성하여 submit
 * gfn_initSelect    		: Select에 Option 세팅
 * gfn_setValueAuto			: element 에 포맷에 맞게 값을 세팅
 * gfn_getRowType           : Input CRUD 체크
 * gfn_getRowTypeStr        : Input CRUD 체크한 문자열 gfn_getRowTypeStr('NUUU')
 * gfn_formatValue			: 문자열 포매팅
 * gfn_formatValueMask      : 형식화된 숫자 문자열을 포매팅
 * gfn_countChr 			: 문자열에서 chr 포함 갯수
 * gfn_countStr 			: 문자열에서 str 포함 갯수
 * gfn_formatComma 			: 숫자 문자열에 ,(콤마)
 * gfn_deletePrecedingZero	: 선행하는 0 제거
 * grn_getNormalValue   	: element 의 포맷 안된 원데이터
 * gfn_restoreValue     	: 포맷 제거한 원 데이터
 * gfn_checkForm            : 폼 Validation
 * gfn_checkInput           : Input Validation
 * gfn_checkFormatInput     : input format check
 * gfn_checkFormatValue     : input format check
 * gfn_getFormatErrMsg      : input validation err msg
 * gfn_between              : val 이 from, to 에 속하는지 체크
 * gfn_checkMinMaxVal       : 최소, 최대값 체크
 * gfn_checkMinMaxByteLen   : 최소, 최대 바이트 체크
 * gfn_getByteLength        : 바이트 체크
 * gfn_input_selectfocus    : input focus
 * gfn_checkFromTo          : 시작일, 종료일 등 시작값이 종료값보다 작은지 체크
 * gfn_checkInputNN         : input Not Null 체크
 * rowspan                  : 테이블 row머지
 * gfn_getUniqueBrchCode	: 공통코드의 사업장코드중 중복 제거후 리턴
 **************************************************************************/  

/************************************************************************
* 함 수 명 : gfn_isNull
* 기    능 : Null 체크
* 인    자 : sValue - 체크대상 값
* 리 턴 값 : Null 여부, Null - true
************************************************************************/ 
gfn_isNull = function(sValue)
{
    if (new String(sValue).valueOf() == "undefined") return true;
    if (sValue == null) return true;
    
    var ChkStr = new String(sValue);

    if (ChkStr == null) return true;
    if (ChkStr.toString().length == 0 ) return true;
    return false;
}

/************************************************************************
* 함 수 명 : gfn_isNum
* 기    능 : 문자열이 숫자형식에 맞는지 여부 체크
* 인    자 : sNum - 체크대상 값
* 리 턴 값 : boolean 체크 여부
************************************************************************/
gfn_isNum = function(sNum)
{
	var c;
	var point_cnt = 0;
	var ret = true;

	if (gfn_isNull(sNum)) 
	{
		return false;
	}

	for (var i = 0; i < sNum.length; i++) 
	{
		c = sNum.charAt(i);
		if (i == 0 && (c == "+" || c == "-")) 
		{
		}
		else if (c >= "0" && c <= "9") 
		{
		}
		else if (c == ".") 
		{
			point_cnt++;
			if (point_cnt > 1) 
			{
				ret = false;
				break;
			}
		}
		else 
		{
			ret = false;
			break;
		}
	}

	return ret;
}

/************************************************************************
* 함 수 명 : gfn_isDigit
* 기    능 : 숫자 정합성 체크
* 인    자 : val - 체크대상 값
* 리 턴 값 : boolean 체크 여부
************************************************************************/
gfn_isDigit = function(val) {
    var lvNum;
    var lvFlag   = "No";
    var lvRetVal = false;
    var lvLength;
    var i;
    
    if (gfn_isNull(val) == true) {
        lvFlag      = "Yes";
        lvRetVal = false;
        return lvRetVal;
    }
    
    lvLength = val.length;
    
    if ((val.toString().split(".").length > 2) && lvFlag == "No") {
        lvFlag   = "Yes";
        lvRetVal = false;
    }
    
    if (lvFlag == "No") {
        for (i = 0; i < lvLength ; i++) {
            lvNum = val.substr(i,1);
            if (i == 0) {
                if(gfn_isNum(lvNum) || (lvNum == "-" && val.substr(1, 1) != ".")
                    || (lvNum == "+"  && val.substr(1, 1) != ".")){
                    lvRetVal = true;
                } else {
                    lvRetVal = false;
                    break;
                }
            } else {
                if(gfn_isNum(lvNum) || (lvNum == "." && val.substr(i+1, 1).length != 0)) {
                    lvRetVal = true;
                } else {
                    lvRetVal = false;
                    break;
                }
            }
        }
    }

    return lvRetVal;
}

/************************************************************************
* 함 수 명 : gfn_isFunction
* 기    능 : 화면에 함수 존재여부 체크
* 인    자 : objForm - 체크대상 Form
*             sFunctionName - 체크대상 Function Name
* 리 턴 값 : boolean 체크 여부
************************************************************************/
gfn_isFunction = function(objForm, sFunctionName)
{
	if (objForm[sFunctionName] == undefined) {
		return false;
	} else {
		// Type 체크
		if (typeof eval("objForm[sFunctionName]") == "function"){
			return true;
		}
	}
	return false;
}

/************************************************************************
* 함 수 명 : gfn_dsPrn
* 기    능 : LogView에 데이터셋의 구조, 값을 확인한다.(개발자용)
* 인    자 : obj - 데이터셋
* 리 턴 값 : 없음
************************************************************************/
gfn_dsPrn = function (obj)
{
	//  Obj : Dataset 콤포넌트
	var iColCnt = obj.colcount;
	var iRowCnt = obj.rowcount;
	var i, j; 
	//------------------------------------------------
	//   Header를 출력한다.
	//------------------------------------------------ 
	var sHeadStr="";
	var sDsName="UnKnown Dataset";
	 
	sDsName = obj.id;
	trace(">>>>    Dataset (" + sDsName + ") Tracing   ");
	trace(">>>> " + "Col Cnt:" + iColCnt +" /  Row Cnt:" + iRowCnt);
	 
	for (j=0;j<iColCnt;j++)
	{
		sHeadStr += "[" + obj.getColID(j) +"] ";  
	}
	trace(">>>> COL ID : " + sHeadStr);
	 
	 //------------------------------------------------
	 //   내용을 출력한다.
	 //------------------------------------------------ 
	var sRowStr="";
	for (i=0;i<iRowCnt;i++)
	{
		sRowStr=obj.getRowType(i) + "::";
		for (j=0;j<iColCnt;j++)
		{
			sRowStr += obj.getColID(j) + "[" + obj.getColumn(i,obj.getColID(j)) +"] ";  
		}
		trace(">>>> " + sRowStr);
	}
	trace(">>>> " + "---------------------------------------------------");
}

/************************************************************************
* 함 수 명 : gfn_getRandom
* 기    능 : 요청한 자리수만큼의 랜덤값 생성 리턴
* 인    자 : nLen - 랜덤값자리수
* 리 턴 값 : 랜덤값
************************************************************************/
gfn_getRandom = function(nLen)
{
	var sRand = new String(Math.ceil(Math.random() * 12345))
	          + new String(Math.ceil(Math.random() * 13579))
	          + new String(Math.ceil(Math.random() * 24680))
	          + new String(Math.ceil(Math.random() * 63221))
	          + new String(Math.ceil(Math.random() * 32423))
	          + new String(Math.ceil(Math.random() * 55667))
	          + new String(Math.ceil(Math.random() * 88990))
	          ;
	return sRand.substr(0, nLen);
}

/************************************************************************
* 함 수 명 : gfn_isCheckDs
* 기    능 : 해당 데이터셋명으로 검색하여 데이터셋 존재여부 리턴(true/false)
* 인    자 : objForm - form object
*            sDsNm - dataset ID
* 리 턴 값 : true / false
************************************************************************/
gfn_isCheckDs = function(objForm, sDsNm)
{
	var objDs = objForm.objects[sDsNm];

	if(gfn_isNull(objDs)) {
		return false;
	} else {
		return true;
	}
}

/************************************************************************
* 함 수 명 : gfn_isCheckDs
* 기    능 : 해당 데이터셋명으로 검색하여 데이터셋이 없으면 데이터셋을 생성
* 인    자 : objForm - form object
*            sDsNm - dataset ID
*            bClear - data clear여부
* 리 턴 값 : object dataset
************************************************************************/
gfn_getDataset = function(objForm, sDsNm, bClear)
{
	var objDs = objForm.objects[sDsNm];

	if(gfn_isNull(objDs)) {
		objDs = new Dataset;
		objDs.set_name(sDsNm);

		var nRtn = objForm.addChild(sDsNm, objDs);
		//trace("nRtn : " + nRtn);
	} else {
		if ( bClear == true ) objDs.clearData();
	}

	return objDs;
}

/************************************************************************
* 함 수 명 : gfn_dsCopy
* 기    능 : 지정한 데이터셋을 특정 Row Type만 필터링하여 복사
* 인    자 : objForm - Form object
*             dsNameOri - 원본 데이터셋명
*             dsNameTar - 사본 데이터셋명
*             rowType - 필터링할 Row Type, N:일반, I:추가, U:수정, D:삭제, 생략시 IUD
*             changeRowType - 변경할 Row Type, 사본의 Row Type을 강제로 변경하는 기능, 생략시 변경안함
* 리 턴 값 : 사본 데이터셋
* 예    제	: gfn_dsCopy(this, "ds_a", "ds_b", "UD") // 데이터셋 'ds_a'의 Row중에 수정,삭제상태 Row만 복사하여 ds_b생성
*             gfn_dsCopy(this, "ds_c", "ds_d", "IU", "I") // 데이터셋 'ds_c'의 Row중에 추가,수정상태 Row만 복사하여 ds_d를 생성하고 사본의 Row Type를 전부 추가상태로 변경
************************************************************************/
 gfn_dsCopy = function(objForm, dsNameOri, dsNameTar, rowType, changeRowType)
 {
	if(rowType == null || rowType == ""){
		rowType = "IUD";
	}
	var c;
	switch(changeRowType){
	case "N":
		c = Dataset.ROWTYPE_NORMAL;
		break;
	case "I":
		c = Dataset.ROWTYPE_INSERT;
		break;
	case "U":
		c = Dataset.ROWTYPE_UPDATE;
		break;
	case "D":
		c = Dataset.ROWTYPE_DELETE;
		break;
	default:
		c = null;
		break;
	}
	
	var ds_ori = objForm[dsNameOri];
	var ds_tar = objForm[dsNameTar] = new Dataset(dsNameTar);
	ds_tar.set_updatecontrol(false);
	
	for(var i=0; i<ds_ori.getColCount(); i++){
		var ci = ds_ori.getColumnInfo(i);
		ds_tar.addColumn(ci.id, ci.type, ci.size);
	}
	
	if(rowType.indexOf("D") != -1){
		var rowDelArr = ds_ori.getDeletedRowset();
		
		for(var i=0; i<rowDelArr.length; i++){
			var row = rowDelArr[i];
			var nRowIdx = ds_tar.addRow();
			for(var j=0; j<ds_ori.getColCount(); j++){
				ds_tar.setColumn(nRowIdx, ds_ori.getColumnInfo(j).id, row[j]);
				ds_tar.setRowType(nRowIdx, c == null ? Dataset.ROWTYPE_DELETE : c);
			}
		}

	}
	
	var isNormal = rowType.indexOf("N") != -1;
	var isInsert = rowType.indexOf("I") != -1;
	var isUpdate = rowType.indexOf("U") != -1;
	
	for(var i=0; i<ds_ori.getRowCount(); i++){
		var t = ds_ori.getRowType(i);
		if(
			(isNormal && t == Dataset.ROWTYPE_NORMAL) ||
			(isInsert && t == Dataset.ROWTYPE_INSERT) ||
			(isUpdate && t == Dataset.ROWTYPE_UPDATE)
		){
			var nRowIdx = ds_tar.addRow();
			for(var j=0; j<ds_ori.getColCount(); j++){
				var colId = ds_ori.getColumnInfo(j).id;
				ds_tar.setColumn(nRowIdx, colId, ds_ori.getColumn(i, colId));
				ds_tar.setRowType(nRowIdx, c == null ? t : c);
			}
		}
	}
	
	return ds_tar;
}

/************************************************************************
* 함 수 명 : gfn_dsToParam
* 기    능 : 지정한 데이터셋을 파라미터로 만든다.
* 인    자 : objDs - 데이터셋
*             arrParamNames - 파라미터로 지정할 이름 배열
*             objParam - 객체로 전달하는 경우, 생략시 문자열
* 리 턴 값 : 파라미터 문자열 또는 객체
* 예    제	: gfn_param(ds_search, ["COMPANY_CD", "DATE"]); => "COMPANY_CD=HS000 DATE=20001231"
              gfn_param(ds_search, ["COMPANY_CD", "DATE"], { test: 1 }); => { test: 1, COMPANY_CD: "HS000",  DATE: "20001231" }
************************************************************************/
gfn_dsToParam = function(objDs, arrParamNames, objParam)
{
	var isStr = !objParam;
	var rtn = isStr ? "" : objParam;
	for(var i=0; i<arrParamNames.length; i++){
		var name = arrParamNames[i];
		var val = objDs.getColumn(0, name);
		if(isStr){
			if(i != 0){
				rtn += " ";
			}
			rtn += name + "=" + val;
		}else{
			rtn[name] = val;
		}
	}
	return rtn;
}

/************************************************************************
* 함 수 명 : gfn_isRsrNo
* 기    능 : 주민 등록번호 확인
* 인    자 : {string} rsrno 주민등록번호 13자리
* 리 턴 값 : true/false
************************************************************************/
gfn_isRsrNo = function(rsrno)
{
	var juminNo = rsrno.replace("-", "");
	juminNo = juminNo.trim();
	
	if (!nexacro.isNumeric(juminNo) || juminNo.length != 13)
	{
		return false;
	}
	
	var juminChkDgt = [2,3,4,5,6,7,8,9,2,3,4,5];
	var fNum = new Number();
	var lNum = new Number();
	var iSum = new Number();
	var yy, lnumf;
	
	fNum = juminNo.substr(0, 6).toString();
	lNum = juminNo.substr(6).toString();
	lnumf = lNum.substr(0,1);
	
	if (lnumf == '1' ||  lnumf == '2' ||  lnumf == '5' ||  lnumf == '6')
	{
		yy  = '19';
	}
	else if (lnumf == '3' ||  lnumf == '4' ||  lnumf == '7' ||  lnumf == '8') 
	{
		yy  = '20';
	}
	else if (lnumf == '9' ||  lnumf == '0') 
	{
		yy  = '18';
	}
	else
	{
		return false;
	}
	
	if(!gfn_isStringDate(yy + fNum))
	{
		return false;
	}
	
	var bForeignerYN = "N";
	if (lNum.substr(0,1) == '5' || lNum.substr(0,1) == '6' || lNum.substr(0,1) == '7' ||  lNum.substr(0,1) == '8')
	{
		bForeignerYN = "Y";
	}
	
	for (var ix = 0; ix < 12 ; ix++)
	{
		iSum += (parseInt(juminNo.substr(ix, 1)) * juminChkDgt[ix]);
	}
	
	iSum = 11 - (iSum%11);
	iSum = iSum % 10;
	
	if(bForeignerYN == "Y") 
	{
		iSum += 2;
	}
	
	if (iSum != (parseInt(juminNo.substr(12, 1))))
	{
		return false;
	}
	
	return true;
}

/************************************************************************
* 함 수 명 : gfn_isCompRegNo
* 기    능 : 사업자 등록번호 확인
* 인    자 : {string} compRegNo 사업자번호
* 리 턴 값 : true/false
************************************************************************/
gfn_isCompRegNo = function(compRegNo)
{
	var compNo = compRegNo.replace("-", "");
	compNo = compNo.trim();
	
	var checkID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);
	var i, Sum=0, c2, remander;

	if (compNo.length != 10) 
	{
		return false;
	}
 
	for (i = 0; i <= 7; i++) 
	{
		Sum += checkID[i] * compNo.charAt(i);
	}
	
	c2 = "0" + (checkID[8] * compNo.charAt(8));
	c2 = c2.substring(c2.length - 2, c2.length);

	Sum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1));
	remander = (10 - (Sum % 10)) % 10 ;

	if (Math.floor(compNo.charAt(9)) != remander) 
	{
		return false;
	}
	
	return true;
}

/************************************************************************
* 함 수 명 : gfn_isCorpRegNo
* 기    능 : 법인 등록번호 확인
* 인    자 : {string} corpRegNo 법인등록번호
* 리 턴 값 : true/false
************************************************************************/
gfn_isCorpRegNo = function(corpRegNo)
{
	var corpNo = corpRegNo.replace("-", "");
	corpNo = corpNo.trim();
	
	var checkID = new Array(1,2,1,2,1,2,1,2,1,2,1,2);
	var i, Sum=0, c2, remander;

	if (corpNo.length != 13) 
	{
		return false 
	}

	for (var i = 0; i < 12; i++) 
	{
		Sum += checkID[i] * corpNo.charAt(i);
	}
	
	if ((10 - (Sum%10))%10 != corpNo.charAt(12))
	{
		return false;
	}
	else
	{
		return true;
	}
}
		
/************************************************************************
* 함 수 명 : gfn_getGender
* 기    능 : 주민번호로 성별을 Return 한다.
* 인    자 : {string} rsrno 주민등록번호 13자리
* 리 턴 값 : "M", "W"
************************************************************************/
gfn_getGender = function(rsrno)
{
	if (!gfn_isRsrNo(rsrno))
	{
		return false;
	}
	
	var vSexGb = rsrno.substr(6,1);

	if (vSexGb == '1' || vSexGb == '3' || vSexGb == '5' || vSexGb == '7') 
	{
		return "M";	// 남자
	} 
	else if (vSexGb == '2' || vSexGb == '4' || vSexGb == '6' || vSexGb == '8') 
	{
		return "W";	// 여자
	} 
	else 
	{
		return "X";
	}
}

/************************************************************************
* 함 수 명 : gfn_getBirthYear
* 기    능 : 주민번호 뒷 첫번째 자리로 년대를 return 한다
* 인    자 : {string} rsrno 주민등록번호 13자리
* 리 턴 값 : 년대
************************************************************************/
gfn_getBirthYear = function(rsrno)
{
	if (!gfn_isRsrNo(rsrno))
	{
		return false;
	}
	
	// ## 주민등록은 100년 단위로 갱신
	// 식별번호 9 : 1800년도부터 1899년에 태어난 남성
	// 식별번호 0 : 1800년도부터 1899년에 태어난 여성	
	// 식별번호 1 : 1900년도부터 1999년에 태어난 남성
	// 식별번호 2 : 1900년도부터 1999년에 태어난 여성	
	// 식별번호 3 : 2000년도부터 2099년에 태어난 남성
	// 식별번호 4 : 2000년도부터 2099년에 태어난 여성	
	// 식별번호 5 : 1900년도부터 1999년에 외국에서 태어난 남성 (귀화/체류/이중국적 남성)
	// 식별번호 6 : 1900년도부터 1999년에 외국에서 태어난 여성 (귀화/체류/이중국적 여성)	
	// 식별번호 7 : 2000년도부터 2099년에 외국에서 태어난 남성 (귀화/체류/이중국적 남성)
	// 식별번호 8 : 2000년도부터 2099년에 외국에서 태어난 여성 (귀화/체류/이중국적 여성)
	
	var lNum = rsrno.substr(6,1);

	if (lNum == '1' ||  lNum == '2' ||  lNum == '5' ||  lNum == '6')
	{
		yy  = '19';
	}
	else if (lNum == '3' ||  lNum == '4' ||  lNum == '7' ||  lNum == '8') 
	{
		yy  = '20';
	}
	else if (lNum == '9' ||  lNum == '0') 
	{
		yy  = '18';
	}
	
	return yy;
}

/************************************************************************
* 함 수 명 : gfn_getAge
* 기    능 : 생년월일 또는 주민 번호로 법적연령-만나이를 구한다.
* 인    자 : birth - {string} rsrno 주민등록번호 13자리 or 생년월일8자리
*            date  - 비교일자
* 리 턴 값 : 만나이
************************************************************************/
gfn_getAge = function(birth, date)
{
	var year;
    var month;
    var day;

    if (birth.length == 13)
    {
    	if (!gfn_isRsrNo(birth))
		{
			return false;
		}
    	
        var vBefor = gfn_getBirthYear(birth);
        if (vBefor == false)
        {
			return false;
		}
		
        birth = vBefor + birth;
        year = parseInt(birth.substr(0,4),10);
        month = parseInt(birth.substr(4,2),10);
        day =   parseInt(birth.substr(6,2),10);
    }
    else if (birth.length == 8)
    {
        year = parseInt(birth.substr(0,4),10);
        month = parseInt(birth.substr(4,2),10);
        day =   parseInt(birth.substr(6,2),10);

		if (!gfn_isStringDate(birth))
        {
            return false;
        }
    }
    else
    {
        return false;
    }

    if (!gfn_isStringDate(date))
    {
        return false;
    }

    var dateYear = parseInt(date.substr(0,4),10);
    var dateMonth = parseInt(date.substr(4,2),10);
    var dateDay = parseInt(date.substr(6,2),10);
    var age = dateYear - year;

    if (month > dateMonth) 
    {
        age = age - 1;
    } 
    else if (month == dateMonth) 
    {
        if (day > dateDay) 
        {
            age = age - 1;
        }
    }

    if (age < 0) 
    {
        age = 0;
    }

    return age;
}

/************************************************************************
* 함 수 명 : gfn_isEmail
* 기    능 : 이메일 주소 체크
* 인    자 : {string} email 이메일 value
* 리 턴 값 : true / false
************************************************************************/
gfn_isEmail = function(email)
{
	if (gfn_isNull(email)) 
	{
		return false;
	}

	var match = email.match(/^(\".*\"|[A-Za-z0-9_-]([A-Za-z0-9_-]|[\+\.])*)@(\[\d{1,3}(\.\d{1,3}){3}]|[A-Za-z0-9][A-Za-z0-9_-]*(\.[A-Za-z0-9][A-Za-z0-9_-]*)+)$/);
	
	if(match == null) 
	{
		return false;
	} 
	else 
	{
		return true;
	}
}

/************************************************************************
* 함 수 명 : gfn_checkSpecialChar
* 기    능 : 특수문자가 있는지 체크, 영자, 한글, 숫자, 간격 문자 이외의 문자로 임의 지정
* 인    자 : {string} value 체크 value
* 리 턴 값 : true / false
************************************************************************/
gfn_checkSpecialChar = function(value)
{
	if (gfn_isNull(value)) 
	{
		return false;
	}
	
	var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

	if( special_pattern.test(value) == true ){
	    return false;
	}

	return true;
}

/************************************************************************
* 함 수 명 : gfn_isHandPhone
* 기    능 : 핸드폰 체크
* 인    자 : {string} phone 핸드폰 value
* 리 턴 값 : true / false
************************************************************************/
gfn_isHandPhone = function(phone)
{
	if (gfn_isNull(phone)) 
	{
		return false;
	}
	
	var match = phone.match(/^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/);
	
	if(match == null) 
	{
		return false;
	} 
	else 
	{
		return true;
	}
}

/************************************************************************
* 함 수 명 : gfn_isPhone
* 기    능 : 일반전화번호 체크
* 인    자 : {string} phone 일반전화번호 value
* 리 턴 값 : true / false
************************************************************************/
gfn_isPhone = function(phone)
{
	if (gfn_isNull(phone)) 
	{
		return false;
	}
	
	var match = phone.match(/^\d{2,3}-\d{3,4}-\d{4}$/);
	
	if(match == null) 
	{
		return false;
	} 
	else 
	{
		return true;
	}
}

/************************************************************************
* 함 수 명 : gfn_telSplit
* 기    능 : 전화번호 하이픈(-)추가, 포커스 아웃에서 호출 (onkillfocus)
* 인    자 : obj - 대상 컴포넌트 또는 문자열
* 리 턴 값 : string / set_value
************************************************************************/
gfn_telSplit = function(obj){
	var target;
	switch(typeof obj){
	case "string":
		target = obj;
		break;
	case "object":
		target = obj.value;
		break;
	default:
		return obj;
	}
	
	var rtn = gfn_telJoin(target);
	var len = rtn.length;
	if(len <= 4){
	}else if(len <= 8){
		rtn = rtn.substr(0, len - 4, 4) + "-" + rtn.substr(len - 4);
	}else if(len <= 11){
		if(rtn.substr(0, 2) == "02"){
			switch(len){
			case 9:
				rtn = "02-" + rtn.substr(2, 3) + "-" + rtn.substr(5);
				break;
			case 10:
				rtn = "02-" + rtn.substr(2, 4) + "-" + rtn.substr(6);
				break;
			}
		}else{
			switch(len){
			case 10:
				rtn = rtn.substr(0, 3) + "-" + rtn.substr(3, 3) + "-" + rtn.substr(6);
				break;
			case 11:
				rtn = rtn.substr(0, 3) + "-" + rtn.substr(3, 4) + "-" + rtn.substr(7);
				break;
			}
		}
	}	
	
	switch(typeof obj){
	case "string":
		return rtn;
	case "object":
		obj.set_value(rtn);
		break;
	}
}

/************************************************************************
* 함 수 명 : gfn_telJoin
* 기    능 : 전화번호 하이픈(-)제거, 포커스 얻을때 사용 (onsetfocus)
* 인    자 : obj - 대상 컴포넌트 또는 문자열
* 리 턴 값 : string / set_value
************************************************************************/
gfn_telJoin = function(obj){
	switch(typeof obj){
	case "string":
		return obj.replace(new RegExp("\-", "g"), "");
		break;
	case "object":
		obj.set_value(obj.value.replace(new RegExp("\-", "g"), ""));
		break;
	default:
		return obj;
	}
}

/************************************************************************
* 함 수 명 : gfn_nvl
* 기    능 : 입력값이 null에 해당되면 val2(대체문자열)로 반환한다.
* 인    자 : val : 체크할 문자열( 예 : null 또는 undefined 또는 "" 또는 "abc" )
* 리 턴 값 : N/A,  val값이 null에 해당하는 경우 val2값으로 대체 반환
************************************************************************/
gfn_nvl = function(val,val2)
{	
    var rtnVal = (gfn_isNull(val)) ? val2 : val;
    return rtnVal;
}


/************************************************************************
* 함 수 명 : gfn_isForeignerNo
* 기    능 : 외국인 등록번호 여부를 확인한다.
* 인    자 : strNo - 입력문자열(등록번호13자리)
* 리 턴 값 : Boolean 형식의 정합성 체크
************************************************************************/
gfn_isForeignerNo = function(strNo) 
{
    if (strNo.length != 13 || !nexacro.isNumeric(strNo)) return false;
    
    var month = nexacro.toNumber(strNo.substr(2, 2));
    var day   = nexacro.toNumber(strNo.substr(4, 2));
    
    if (month < 1 || month > 12) return false;
    if (day < 1 || day > 31) return false;
    
    var sum = 0;
    var odd = 0;
    var buf = array(13);
    var multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
    
    for (var i=0; i<13; i++) {
        buf[i] = nexacro.toNumber(strNo.charAt(i));
    }
    
    if (buf[11] < 6) return false;
    
    odd = buf[7] * 10 + buf[8];
    if((odd%2) != 0) return false;
    
    for (var i=0; i<12; i++) {
        sum += (buf[i] * multipliers[i]);
    }
    
    sum = 11 - (sum % 11);
    
    if (sum >= 10) sum -= 10;
    sum += 2;
    if (sum >= 10) sum -= 10;
    
    if (buf[12] == sum) {
        return true;
    } else {
        return false;
    }
}

/************************************************************************
* 함 수 명 : gfn_jsonObjectToString
* 기    능 : JSON Object를 JSON String으로 변환하여 리턴한다.
* 인    자 : object - JSON Object
* 리 턴 값 : JSON String
************************************************************************/
gfn_jsonObjectToString = function(object) 
{
    var isArray = (object.join && object.pop && object.push
                    && object.reverse && object.shift && object.slice && object.splice);
    var results = [];
    
    for (var i in object) {
        var value = object[i];
        
        if (typeof value == "object"){
            results.push((isArray ? "" : "\"" + i.toString() + "\" : ") + gfn_jsonObjectToString(value));
        }else if (value){
			value = gfn_addSlash(value);
            results.push((isArray ? "" : "\"" + i.toString() + "\" : ") + (typeof value == "string" ? "\"" + value + "\"" : value));
        }
    }
    
    return (isArray ? "[" : "{") + results.join(", ") + (isArray ? "]" : "}");
}

/************************************************************************
* 함 수 명 : gfn_jsonStringToObject
* 기    능 : JSON String을 JSON Object으로 변환하여 리턴한다.
* 인    자 : strJson - JSON String
* 리 턴 값 : JSON Object
************************************************************************/
gfn_jsonStringToObject = function(strJson)
{
    return eval("(" + strJson + ")");
}

/************************************************************************
* 함 수 명 : gfn_addSlash
* 기    능 : value값에 슬레쉬값을 add하여 리턴
* 인    자 : strValue - String
* 리 턴 값 : String
************************************************************************/
gfn_addSlash = function(strValue)
{
	var str=strValue;
	if(typeof(str)=="string"){
		str=str.replace('\\','\\\\');
		str=str.replace('"','\\"');
		str=str.replace('\'','\\\'');
		str=str.replace('\n','\\n');
		str=str.replace('\r','\\r');
		//str=str.replace('\s','\\s');
		str=str.replace('\t','\\t');
	}
	return str;
}

/************************************************************************
* 함 수 명 : gfn_round
* 기    능 : 주어진 수식을 반올림하여 가장 가까운 정수 또는 고정 소수점 실수를 반환
* 인    자 : val - 숫자값
*             digit - 자리수
* 리 턴 값 : Float 정수 또는 부동 소수점 숫자
************************************************************************/
gfn_round = function(val, digit)
{
    if (gfn_isNull(digit))
        digit = 0;
        
    var nRetVal = Math.round(val, digit);

    return nRetVal;
}

/************************************************************************
 * 함 수 명 : gfn_getUniqArr
 * 기    능 : Array에 있는 값들을 Distinct(중복제거)한다.
 * 인    자 : arr - 중복을 제거할 원래 Array ( 예 : (1,1,2,2,3,4,5) )
 * 리 턴 값 : Array 
 ************************************************************************/
gfn_getUniqArr = function(arr)
{
	var result = [];
	$.each(arr, function (i, item){
	    if ($.inArray(item, result) == -1){
	        result.push(item);
	    }
	});
	return result;
}

/************************************************************************
* 함 수 명 : gfn_findArray
* 기    능 : 1차원 배열에서 입력된 문자열이 있는 index를 반환
* 인    자 : ArrObj - 1차원 배열
*            strKey - 검색할 문자열
* 리 턴 값 : Integer 찾은 배열 index
************************************************************************/
gfn_findArray = function(ArrObj, strKey)
{
	for (var i = 0; i < ArrObj.length; i++) 
	{
		if (strKey == ArrObj[i]) 
		{
			return i;
		}
	}
	return -1;
}

/************************************************************************
* 함 수 명 : gfn_findArray2
* 기    능 : 2차원 배열에서 입력된 문자열이 있는 index를 반환
* 인    자 : Arr2Obj - 2차원 배열
*            strKey  - 검색할 문자열
*            index   - 검색할 차원
* 리 턴 값 : Integer 찾은 배열 index
************************************************************************/
gfn_findArray2 = function(Arr2Obj, strKey, index)
{
	for (var i = 0; i < Arr2Obj.length; i++) 
	{
		if (strKey == Arr2Obj[i][index]) 
		{
			return i;
		}
	}
	return -1;
}

/************************************************************************
* 함 수 명 : gfn_getDatasetInfo
* 기    능 : Dataset의 컬럼정보를 얻는다.
* 인    자 : objDs - Dataset Object
* 리 턴 값 : ColumnInfo 배열
************************************************************************/
gfn_getDatasetInfo = function(objDs) 
{
	var objColInfo = new Array();

	//데이터셋 컬럼정보 설정 
	for (var i=0; i < objDs.getColCount(); i++) {
		objColInfo[i] = objDs.getColumnInfo(i);
	}
	
	return objColInfo;
}

/************************************************************************
* 함 수 명 : gfn_getObjType
* 기    능 : Object의 type을 문자열로 얻어온다.
* 인    자 : obj - type을 얻어올 Object
* 리 턴 값 : string Object의 type문자열(예 : "MainFrame", "ChildFrame", "Form", "Button",...)
************************************************************************/
gfn_getObjType = function(oObj)
{
    var sType;

    if (gfn_isNull(oObj))
    {
        return "";
    }

    sType = oObj.toString().valueOf().substr(1, 6);
    if (sType.toLowerCase() == "object")
    {
        return gfn_midString(oObj.toString().valueOf(), " ", "]");
    }

    return "";
}

/************************************************************************
* 함 수 명 : gfn_findData
* 기    능 : dataSet object에서 키에 해당되는 Row를 찾아서 rowpostion 전달
* 인    자 : ObjDs     - dataset
*             strIdCol  - 키칼럼
*             strId     - 키값
*             strSubCol - 서브키 칼럼
*             strSubId  - 서브키값
* 리 턴 값 : rowpostion
************************************************************************/
gfn_findData = function(ObjDs, strIdCol, strId, strSubCol, strSubId)
{
	if (gfn_isNull(strSubCol)) 
	{
		return ObjDs.findRow(strIdCol, strId);
	}
	return ObjDs.findRowExpr(strIdCol + " == '" + strId + "' && " + strSubCol + " == '" + strSubId + "'");		
}

/************************************************************************
* 함 수 명 : gfn_deleteData
* 기    능 : dataset object에서 키에 해당되는 Row를 찾아서 삭제
* 인    자 : ObjDs     - dataset
*             strIdCol  - 키칼럼
*             strId     - 키값
*             strSubCol - 서브키 칼럼
*             strSubId  - 서브키값
* 리 턴 값 : void
************************************************************************/
gfn_deleteData = function(ObjDs, strIdCol, strId, strSubCol, strSubId)
{
	var curRow = gfn_findData(ObjDs, strIdCol, strId, strSubCol, strSubId);
	ObjDs.deleteRow(curRow);
}

/************************************************************************
* 함 수 명 : gfn_moveData
* 기    능 : dataset object에서 키에 해당되는 Row를 찾아서 이동
* 인    자 : ObjDs     - dataset
*             strIdCol  - 키칼럼
*             strId     - 키값
*             strSubCol - 서브키 칼럼
*             strSubId  - 서브키값
* 리 턴 값 : void
************************************************************************/
gfn_moveData = function(ObjDs, strIdCol, strId, newRow, strSubCol, strSubId)
{
	var curRow = gfn_findData(ObjDs, strIdCol, strId, strSubCol, strSubId);
	ObjDs.moveRow(curRow, newRow);
}

/************************************************************************
* 함 수 명 : gfn_editData
* 기    능 : dataset object에서 키에 해당되는 Row를 찾아서 값을 변경
* 인    자 : ObjDs     - dataset
*             strIdCol  - 키칼럼
*             strId     - 키값
*             valCol    - 변경할 칼럼
*             strSubCol - 서브키 칼럼
*             strSubId  - 서브키값
* 리 턴 값 : boolean
************************************************************************/
gfn_editData = function(ObjDs, strIdCol, strId, valCol, newVal, strSubCol, strSubId)
{
	var curRow = gfn_findData(ObjDs, strIdCol, strId, strSubCol, strSubId);
	return ObjDs.setColumn(curRow, valCol, newVal);
}

/************************************************************************
* 함 수 명 : gfn_allRowDataCall
* 기    능 : Dataset의 모든 행을 함수의 인자로 넘겨주고 함수 호출처리
* 인    자 : ObjDs         - 대상 dataset
*             callFncObj    - 호출할 함수
*             bModifiedOnly - 변경된 데이터만 호출할지 여부
* 리 턴 값 : void / false
************************************************************************/
gfn_allRowDataCall = function(objDs, callFncObj, bModifiedOnly)
{
	var retVal;
	for (var i = 0; i < objDs.getRowCount(); i++) 
	{
		if (bModifiedOnly && !(objDs.getRowType(i) == 2 || objDs.getRowType(i) == 4)) 
		{
			continue;
		}
		retVal = callFncObj(objDs, i);
		if (gfn_isNull(retVal) == false) 
		{
			return retVal;
		}
	}
}

/************************************************************************
* 함 수 명 : serializeObject
* 기    능 : FORM 을 JSON 으로 반환
* 인    자 : $('#form').serializeObject();
* 리 턴 값 : JSON
************************************************************************/
$.fn.serializeObject = function () {
    "use strict";
    var result = {};
    var extend = function (i, element) {
        var node = result[element.name];
        if ('undefined' !== typeof node && node !== null) {
           if ($.isArray(node)) {
        	   node.push(element.value);
           } else {
        	   result[element.name] = [node, element.value];
           }
        } else {
     	   if($("#"+element.name).attr('data_format')) result[element.name] = gfn_restoreValue(element.value,$("#"+element.name).attr('data_format'));
     	   else result[element.name] = element.value;
        }
    };
 
    $.each(this.serializeArray(), extend);
    return result;
};

/************************************************************************
* 함 수 명 : gfn_isUpdateDs
* 기    능 : dataSet의 Row 중에서 변경된 내용이 있는지 여부
* 인    자 : objDs     - 확인 대상 Dataset
* 리 턴 값 : boolean
************************************************************************/
gfn_isUpdateDs = function(objDs)
{	
    var bRtn = false;
    var sFilterStr = (objDs.filterstr).toString();
    
    // Filter되어있을 경우 UnFilter해서
    // 필터되어있던 Row에 대해서도 Update가 발생했는지 체크한다
    if(sFilterStr.length>0)    objDs.filter("");
    
    // 삭제건수 확인
    if (objDs.updatecontrol) {
        if (objDs.getDeletedRowCount() > 0) {
            // 필터가 되어있었을 경우 다시 필터해준다.
            if(sFilterStr.length>0) objDs.filter(sFilterStr);
            return true;
        }
    }
    
    var iRowType;
    
    for (var i=0; i<objDs.getRowCount(); i++) {
        iRowType = objDs.getRowType(i);
        
        // insert, update, delete 확인
        if (iRowType == 2 || iRowType == 4 || iRowType == 8) {
            bRtn = true;
            break;
        }
    }
    
    // 필터가 되어있었을 경우 다시 필터해준다.
    if(sFilterStr.length > 0) objDs.filter(sFilterStr);
    
    return bRtn;    	
}

/************************************************************************
* 함 수 명 : gfn_isUpdatedRow
* 기    능 : dataSet의 Row가 변경되었는지 판단하는 함수
* 인    자 : objDs     - 확인 대상 Dataset
*             nRow      - row값
* 리 턴 값 : boolean
************************************************************************/
gfn_isUpdatedRow = function(objDs, nRow)
{
	if (objDs.updatecontrol == true) 
	{
		if (objDs.getRowType(nRow) == 2 || objDs.getRowType(nRow) == 4) 
		{
			return true;
		}
		return false;
	}
	else 
	{
		for (var i = 0; objDs.getColCount(); i++) 
		{
			if (gfn_isUpdateColumn(objDs, nRow, i) == true) 
			{
				return true;
			}
		}
	}
	return false;
}

/************************************************************************
* 함 수 명 : gfn_isUpdateColumn
* 기    능 : dataSet의 Row 에서 해당 칼럼이 변경되었는지 판단하는 함수
* 인    자 : objDs     - 확인 대상 Dataset
*             nRow      - row값
*             Column    - 칼럼명
* 리 턴 값 : boolean
************************************************************************/
gfn_isUpdateColumn = function(objDs, nRow, Column)
{
	if (objDs.getRowType(nRow) == 2) 
	{
		if (gfn_isNull(objDs.getColumn(nRow, Column))) 
		{
			return false;
		}
	}
	else 
	{
		if (objDs.getColumn(nRow, Column) == objDs.getOrgColumn(nRow, Column)) 
		{
			return false;
		}
	}
	return true;
}

/************************************************************************
* 함 수 명 : gfn_dsGetUpdated
* 기    능 : value를 Dataset에 적용 후 Datset의 갱신여부를  Return 한다.
* 인    자 : objComp   - 확인 대상 Component
*            objDs     - 확인 대상 Dataset
* 리 턴 값 : boolean
************************************************************************/
gfn_dsGetUpdated = function(objComp, objDs)
{
	objComp.updateToDataset();
	return gfn_isUpdateDs(objDs);
}

/************************************************************************
* 함 수 명 : gfn_updateToDatasetAll
* 기    능 : 콤포넌트의 변경된 값을 데이타셋에 반영(탭 변경시등 사용)
* 인    자 : obj - form, Div object
* 리 턴 값 : void
************************************************************************/
gfn_updateToDatasetAll = function(obj)
{
    var componentList = obj.components;
    var strType;
    var strSTyle;
    
    for ( var i=0; i<componentList.length; i++ ) {	    
        strType  = componentList[i].toString().toUpperCase();
		strName  = componentList[i].name;
        
        if ( strType == "[OBJECT IMAGEVIEWER]") continue;			// IMAGEVIEWER 제외     
        if ( strType == "[OBJECT STATIC]") continue;				// STATIC 제외       
        if ( strType == "[OBJECT BUTTON]") continue;				// BUTTON 제외 
        //if ( strType == "ObjPaging") continue;					// ObjPaging 제외      
        //if ( strType == "ObjAtchFile") continue;					// ObjAtchFile 제외      

		//trace("strType : " + strType + " / strName : " + strName);
                
        // TAB, TABPAGE, DIV, POPUPDIV
        if ( strType == "[OBJECT TAB]" || strType == "[OBJECT TABPAGE]"
             || strType == "[OBJECT DIV]" || strType == "[OBJECT POPUPDIV]" ) {
            gfn_updateToDatasetAll(componentList[i]);
        } 
        else {
			componentList[i].updateToDataset();
        }      
    }
}

/************************************************************************
* 함 수 명 : gfn_setComboValue
* 기    능 : combo dataset에 data를 추가
* 인    자 : objCbo   - combo object
*            objDs    - innerdataset object
*            sCd      - code 값
*            sText    - text
*            nRow     - insert 될 row
*            nIdx     - combo에서 선택 되어질 index
*            sValue   - combo에서 선택 되어질 값
* 리 턴 값 : void
************************************************************************/
gfn_setComboValue = function(objCbo, objDs, sCd, sText, nRow, nIdx, sValue)
 {
	if(gfn_isNull(sCd))		sCd = "";
	
	if(gfn_isNull(sText))	sText = "";
	
	if(gfn_isNull(nRow))	nRow = 0;

	try {
		objDs.insertRow(nRow);
		objDs.setColumn(nRow, objCbo.codecolumn, sCd);
		objDs.setColumn(nRow, objCbo.datacolumn, sText);
	} catch(e) {
		trace("Combo : " + objCbo.name + " 의 innerdataset을 재지정해 주세요.");
	}
	// combo값 지정
	if(!gfn_isNull(nIdx)) {
		// combo값을 index로 지정
		objCbo.set_index(nIdx);
	}
	if(!gfn_isNull(sValue)) {
		// combo값을 값으로 지정
		objCbo.set_value(sValue);
	} else {
		// combo값을 index로 지정
		objCbo.set_index(0);
	}
}

/**
 * @Description : JQuery Alert or Confirm
 * @param : title
 * @param : msg
 * @param : trueCallback
 * @param : falseCallback
 */
var jsAlert = {		
	alert : function(title, msg, obj, callback) {
		if(isEmpty(title)) title = "알림";
		jAlert(msg, title, function(result){
			if (result) {
				if (typeof (obj) != 'undefined') $(obj).focus(); 
				if (typeof (callback) != 'undefined') eval(callback);
			}
		});
	},
	confirm : function(title, msg, trueCallback, falseCallback) {
		if(isEmpty(title)) title = "확인";
		jConfirm(msg, title, function(result) {			
			if (result) {
				eval(trueCallback);
			} else {
				if (falseCallback != "")
					eval(falseCallback);
			}
		});
	},
	prompt : function(title, msg, inputVal, callback) {
		jPrompt(msg, inputVal, title, function(r) {
		    if( r ) {
//		    	alert('You entered ' + r);
	    		inputVal.val(r);
	    		eval(callback);
		    } else {
		    	jsAlert.alert("경고", "취소사유를 입력해 주세요.", "");
		    }
		});
	}	
}


/************************************************************************
* 함 수 명 : gfn_makePhoneCombo
* 기    능 : 전화번호 앞자리 콤보박스를 만든다.
* 인    자 : selectId - 콤보박스 아이디
* 리 턴 값 : 콤보박스
************************************************************************/
gfn_makePhoneCombo = function(selectId, val)
{
	var phoneNum = ["010","011","016","017","018","019","02","031","032","033","041","042","043","044","051","052","053","054","055","061","062","063","064","070","080"];
	selectId = typeof selectId == "string" ? ((selectId.indexOf("#") > -1)?$(selectId):$("#" + selectId)) : $(selectId);
	$.each(phoneNum, function (i, item) {
		selectId.append($('<option>', { 
	        value: item,
	        text : item
	    }));
	});
	
	if(val) selectId.val(val).prop("selected", true);
}

/************************************************************************
* 함 수 명 : gfn_makeMobileCombo
* 기    능 : 휴대폰 앞자리 콤보박스를 만든다.
* 인    자 : selectId - 콤보박스 아이디
* 리 턴 값 : 콤보박스
************************************************************************/
gfn_makeMobileCombo = function(selectId, val)
{
	var phoneNum = ["010","011","016","017","018","019"];
	selectId = typeof selectId == "string" ? ((selectId.indexOf("#") > -1)?$(selectId):$("#" + selectId)) : $(selectId);
	$.each(phoneNum, function (i, item) {
	    selectId.append($('<option>', { 
	        value: item,
	        text : item
	    }));
	});
	
	if(val) selectId.val(val).prop("selected", true);
}

/************************************************************************
* 함 수 명 : gfn_makeFaxCombo
* 기    능 : 팩스 앞자리 콤보박스를 만든다.
* 인    자 : selectId - 콤보박스 아이디
* 리 턴 값 : 콤보박스
************************************************************************/
gfn_makeFaxCombo = function(selectId, val)
{
	var phoneNum = ["02","031","032","033","041","042","043","044","051","052","053","054","055","061","062","063","064","0503","070"];
	selectId = typeof selectId == "string" ? ((selectId.indexOf("#") > -1)?$(selectId):$("#" + selectId)) : $(selectId);
	$.each(phoneNum, function (i, item) {
	    selectId.append($('<option>', { 
	        value: item,
	        text : item
	    }));
	});
	
	if(val) selectId.val(val).prop("selected", true);
}

/************************************************************************
 * 함 수 명 : gfn_makeEmailCombo
 * 기    능 : 이메일 콤보박스를 만든다.
 * 인    자 : selectId - 콤보박스 아이디
 * 리 턴 값 : 콤보박스
 ************************************************************************/
gfn_makeEmailCombo = function(selectId, val)
{
	var mailAddr = ["daum.net","gmail.com","hanmail.net","hotmail.com","nate.com","naver.com"];
	selectId = typeof selectId == "string" ? $("#" + selectId) : $(selectId);
	selectId.append('<option value="" selected="">직접입력</option>');
	$.each(mailAddr, function (i, item) {
		selectId.append($('<option>', { 
			value: item,
			text : item
		}));
	});
	
	if(val) selectId.val(val).prop("selected", true);
}

/************************************************************************
 * 함 수 명 : gfn_makeNumberCombo
 * 기    능 : 숫자 콤보박스를 만든다.
 * 인    자 : selectId, str, end, suffix
 * 리 턴 값 : 콤보박스
 ************************************************************************/
gfn_makeNumberCombo = function(selectId, start, end, suffix)
{
	selectId = typeof selectId == "string" ? $("#" + selectId) : $(selectId);
	for(idx=Number(start); idx <= Number(end); idx++){
		var txt = (suffix)?String(idx)+suffix:idx;
		selectId.append($('<option>', { 
			value: idx,
			text : txt
		}));
	};
}

/************************************************************************
* 함 수 명 : gfn_getCmnCode
* 기    능 : FO 서비스를 통해서 공통코드 조회 하기
* 인    자 : grpCd - 그룹코드
* 리 턴 값 : 콤보데이터
************************************************************************/
gfn_getCmnCode = function(grpCd)
{
	var rtnData;
	$.ajax({
		url : $("#myContextPath").val()+"/rst/cmn/getCmnCode.mvc",
		type: "POST",
		async: false,  
		data: {
			INTF_ID: "TFO00HBSSCMCMN0003",
			RECV_SVC_CD: "HBSSCMCMN0003",
			GRP_CD: grpCd,
			LANG_CD: "KO",
			CD_NM: "",
			USE_YN: "Y",
			SET1_VAL: "",
			SYSTEM_YN: "Y"
		},
		success: function(data) {
			var messageHeader = gfn_getMessageHeader( data );
			
			if( messageHeader[0] != "0" ){
				cmnAlert("errer : " + messageHeader[1]);
			}else{
				rtnData = data.ds.Data.ds_codeList;
			}
		},
		error: function(request,status,error) {
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	
	return rtnData; 
}

/************************************************************************
* 함 수 명 : gfn_getCommonCode
* 기    능 : FO 서비스를 통해서 공통코드 조회 하기
* 인    자 : grpCd - 그룹코드
* 리 턴 값 : 콜백&콤보데이터
************************************************************************/
function gfn_getCommonCode(grpCd, callback){
	$.ajax({
		url : $("#myContextPath").val()+"/rst/cmn/getCmnCode.mvc",
		type: "POST",
		data: {
			INTF_ID: "TFO00HBSSCMCMN0003",
			RECV_SVC_CD: "HBSSCMCMN0003",
			GRP_CD: grpCd,
			LANG_CD: "KO",
			CD_NM: "",
			USE_YN: "Y",
			SET1_VAL: "",
			SYSTEM_YN: "Y"
		},
		success: function(data) {
			var messageHeader = gfn_getMessageHeader( data );
			
			if( messageHeader[0] != "0" ){
				cmnAlert("errer : " + messageHeader[1]);
			}else{
				eval(callback);
			}
		},
		error: function(request,status,error) {
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

/************************************************************************
* 함 수 명 : gfn_getCd
* 기    능 : 코드값 반환
* 인    자 : cdArrName, cd, rType
* 리 턴 값 : String
************************************************************************/
function gfn_getCd(cdArrName, cd, rType){
	var rtn;
	if(gfn_isNull(cd)) return '';
	if(gfn_isNull(rType)) rType = 'NM';
	$.each(eval(cdArrName), function (i, item) {
		if(cd == item.CMON_CD || cd == item.CMCD_NM) {
			if(rType == 'NM'){
				rtn = item.CMCD_NM;
			} else if(rType == 'CD'){
				rtn = item.CMON_CD;
			} else {
				rtn = item.rType;
			}
		}
	});
	return rtn;
}

/************************************************************************
* 함 수 명 : Jquery alerts
* 기    능 : Alert, Confirm 창 
* 인    자 : message, title, callback
* 리 턴 값 : callback
************************************************************************/
(function($) {
	
	$.alerts = {
		verticalOffset: -75,                
		horizontalOffset: 0,                
		repositionOnResize: true,           
		overlayOpacity: .01,                
		overlayColor: '#FFF',               
		draggable: true,                    
		okButton: '&nbsp;확인&nbsp;',         
		cancelButton: '&nbsp;취소&nbsp;', 
		dialogClass: null,                  
		returnVal: {}, 
		returnFn: new Object(), 
		isPpoup: false,
		
		alert: function(message, title, callback) {
			if( title == null ) title = '';
			$.alerts._show(title, message, null, 'alert', function(result) {
				if( callback ) callback(result);
			});
		},
		
		confirm: function(message, title, callback) {
			if( title == null ) title = '';
			$.alerts._show(title, message, null, 'confirm', function(result) {
				if( callback ) callback(result);
			});
		},

		popup: function(param, title, callback) {
			if( title == null ) title = '';
			$.alerts._show(title, param, null, 'popup', callback);
		},
			
		_show: function(title, msg, value, type, callback) {
			
			$.alerts._hide();
			
			$.alerts._overlay('show', type);
				
			$("BODY").append(
				 '<div id="popup_container" style="box-shadow: 2px 2px 15px #888888">' +
				    '<h1 id="popup_title"></h1>' +
				    '<div id="popup_content">' +
				      '<div id="popup_message"></div>' +
					'</div>' +
					'<a href="#" id="popup_close" class="close">닫기</a>'+
				 '</div>');
			
			if( $.alerts.dialogClass ) $("#popup_container").addClass($.alerts.dialogClass);
			
			var pos = 'fixed'; 
			
			$("#popup_container").css({
				position: pos,
				zIndex: 99999,
				padding: 0,
				margin: 0
			});
			
			$("#popup_title").text(title);
			
			$.alerts._reposition();
			$.alerts._maintainPosition(true);
			
			switch( type ) {
				case 'alert':
					$("#popup_container").css({
						minWidth: $("#popup_container").outerWidth(),
						maxWidth: $("#popup_container").outerWidth()
					});
					$("#popup_content").addClass(type);
					$("#popup_message").text(msg);
					$("#popup_message").html( $("#popup_message").text().replace(/\n/g, '<br />') );
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" class="ui_button_2 black"/></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_close").click( function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress( function(e) {
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
				break;
				
				case 'confirm':
					$("#popup_container").css({
						minWidth: $("#popup_container").outerWidth(),
						maxWidth: $("#popup_container").outerWidth()
					});
					$("#popup_content").addClass(type);
					$("#popup_message").text(msg);
					$("#popup_message").html( $("#popup_message").text().replace(/\n/g, '<br />') );
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" class="ui_button_2 black"/> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" class="ui_button_2 black"/></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						if( callback ) callback(true);
					});
					$("#popup_cancel").click( function() {
						$.alerts._hide();
						if( callback ) callback(false);
					});
					$("#popup_close").click( function() {
						$.alerts._hide();
						if( callback ) callback(false);
					});					
					$("#popup_ok").focus();
					$("#popup_ok, #popup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#popup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');
					});
				break;
				
				case 'popup':
					$.alerts.returnVal = {};
					$.alerts.returnFn = callback;
					$.alerts.draggable = false;
					var tmpId = (new Date()).getTime();
					$("#popup_content").html( "<iframe id='dialogframe_" + tmpId + "' name='dialogframe_" + tmpId + "' frameborder='0' src='about:blank' scrolling='no' onfocus='blur()'></iframe>" );
					
					$("#popup_content").closest('#popup_container').hide();
					
					msg.POPUP_ID = 'dialogframe_' + tmpId;
					
					submit2({target: "dialogframe_"+tmpId, action: msg.POPUP_URL}, msg, true);

					$("#popup_close").click( function() {
						$.alerts._hide('popup');
						if( callback ) {
							callback($.alerts.returnVal);
						}
					});					
					break;
			}
			
			if( $.alerts.draggable ) {
				try {
					$("#popup_container").draggable({ handle: $("#popup_title") });
					$("#popup_title").css({ cursor: 'move' });
				} catch(e) { /* requires jQuery UI draggables */ }
			}
		},
		
		_hide: function(type) {
			$("#popup_container").remove();
			if (type == 'popup'){
				Progress.stop();
			} else {
				$.alerts._overlay('hide');
			}
			$.alerts._maintainPosition(false);
		},
		
		_overlay: function(status, type) {
			var sOpacity = '';
			if($.alerts.isPpoup){
				sOpacity = '0.0';
			} else {
				sOpacity = '0.4';
			}
			switch( status ) {
				case 'show':
					if (type == 'popup'){
						Progress.start();
					} else {
						$.alerts._overlay('hide');
						$("BODY").append('<div id="popup_overlay"></div>');
						$("#popup_overlay").css({
							position: 'absolute',
							zIndex: 99998,
							top: '0px',
							left: '0px',
							width: '100%',
							height: $(document).height(),
							opacity: sOpacity,
							background: '#000'
						});
					} 
					break;
				case 'hide':
					$("#popup_overlay").remove();
					break;
			}
		},
		
		_reposition: function(type) {
			var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
			var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;
			if( top < 0 ) top = 0;
			if( left < 0 ) left = 0;
			
			$("#popup_container").css({
				  "top" : '50%',
		    	  "left" : '50%',
		    	  "transform" : 'translate(-50%, -50%)'
			});
			$("#popup_overlay").height( $(document).height() );
		},
		
		_maintainPosition: function(status) {
			if( $.alerts.repositionOnResize ) {
				switch(status) {
					case true:
						$(window).bind('resize', $.alerts._reposition);
					break;
					case false:
						$(window).unbind('resize', $.alerts._reposition);
					break;
				}
			}
		}
		
	}
	
	cmnAlert = function(message, title, callback) {
		$.alerts.alert(message, title, callback);
	}
	
	cmnConfirm = function(message, title, callback) {
		$.alerts.confirm(message, title, callback);
	};

	cmnPopup = function(message, title, callback) {
		$.alerts.popup(message, title, callback);
	};
	
	sizeDialog = function(sWidth, sHeight, el) {
      var modal = parent.$("#"+el).parent().parent();
      var popframe = parent.$("#"+el);
      parent.$("#"+el).closest('#popup_container').show().delay(2);
      $.alerts.isPpoup = true;
      Progress.stop();
      modal.css({
    	  "width" : sWidth+'px',
    	  "height" : sHeight+'px',
    	  "top" : '50%',
    	  "left" : '50%',
    	  "transform" : 'translate(-50%, -50%)'
      });
      
      popframe.css({
    	  "width" : modal.width()-80+'px',
    	  "height" : modal.height()-100+'px'
      });
	};
	
	popClose = function(el) {
		parent.$("#popup_close").trigger('click');
	};
	
	returnVal = function(rvObj) {
		parent.$.alerts.returnVal = rvObj; 
	};
	
	
})(jQuery);

/************************************************************************
* 함 수 명 : submit2
* 기    능 : form 생성하여 submit, 페이지 호출시 사용 (DataMap)
* 인    자 : property, parameters
* 리 턴 값 : null
************************************************************************/
function submit2(property, obj, popYn)
{
  var form = $("<form></form>").attr(property);
  $(form).attr("method", property.method || "post");
  for (var key in obj) {
      if (obj.hasOwnProperty(key)) {
    	  if(encodeURIComponent(key)=="POUP_URL") confinue;
    	  form.append($("<input>").attr({name: encodeURIComponent(key), type: "hidden", value: encodeURIComponent(obj[key])}));
      }
  }
  if(popYn) form.append($("<input>").attr({name: "_popup", type: "hidden", value: "true"}));
  form.insertAfter(document.body);
  form.submit();
  form.remove();
  form = null;
}

/************************************************************************
 * 함 수 명 : submitJson
 * 기    능 : form 생성하여 submit, param을 JSON(ds:{})으로 전달, Ajax호출시 사용 (MciMap)
 * 인    자 : property, parameters
 * 리 턴 값 : null
 ************************************************************************/
function submitJson(property, obj)
{
	var form = $("<form></form>").attr(property);
	$(form).attr("method", property.method || "post");
	form.append($("<input>").attr({name: 'ds', type: "hidden", value: JSON.stringify(obj)}));
	form.insertAfter(document.body);
	form.submit();
	form.remove();
	form = null;
}

/************************************************************************
 * 함 수 명 : submitExDn
 * 기    능 : form 생성하여 submit
 * 인    자 : property, parameters
 * 리 턴 값 : null
 ************************************************************************/
function submitExDn(property, param)
{
	var frame = $("<iframe name='downFile' style='display:none'></iframe>");
    var form = $("<form></form>").attr(property);
    $(form).attr("method", "post");
    $(form).attr("target", "downFile");
    for (var key in param) {
	    if (param.hasOwnProperty(key)){
		    var obj = param[key];
    	    if (Array.isArray(param[key])){
			    var obj = param[key][0];
			    for (var key2 in obj) {
			        if (obj.hasOwnProperty(key2)) {
			    	    form.append($("<input>").attr({name: encodeURIComponent(key)+'[0]['+encodeURIComponent(key2)+']', type: "hidden", value: encodeURIComponent(obj[key2])}));
			        }
			    }
    	    } else if(typeof param[key] == 'object'){
			    var obj = param[key];
			    for (var key2 in param[key]) {
			        if (param[key].hasOwnProperty(key2)) {
			    	    form.append($("<input>").attr({name: encodeURIComponent(key)+'['+encodeURIComponent(key2)+']', type: "hidden", value: encodeURIComponent(obj[key2])}));
			        }
			    }
    	    } else {
		        if (param.hasOwnProperty(key)) {
		    	    form.append($("<input>").attr({name: encodeURIComponent(key), type: "hidden", value: encodeURIComponent(param[key])}));
		        }
    	    }
	    }
    }
    if($("iframe[name='downFile']").length==0){
    	frame.insertAfter(document.body);
    }
    form.insertAfter(document.body);
    form.submit();
    form.remove();
    form = null;	
}

/************************************************************************
* 함 수 명 : gfn_initSelect
* 기    능 : element 에 포맷에 맞게 값을 세팅
* 인    자 : el, data, option, val
* 리 턴 값 : Option set
************************************************************************/
function gfn_initSelect(el, data, option, val, condSeq, condVal){
	var nm = el;
	nm = typeof nm == "string" ? nm : nm.attr("id");
	el = typeof el == "string" ? $("#" + el) : $(el);
	if ( el.length == 0 ) console.log("gfn_initSelect: select('id="+nm+"') is not defined...");
	if(option!='U') el.empty();
    switch ( option )
    {
      case "A": el.append($('<option>', {value: "", text: "전체" })); break;
      case "S": el.append($('<option>', {value: "※", text: "선택" })); break;
      case "N": el.append($('<option>', {value: "", text: "" })); break;
    }	
	
	$.each(data.ds.Data.ds_codeList, function (i, item) {
		flag = true;
		if(condSeq){ //조건에 맞는 코드만 생성시 사용
			if((condSeq == 1 && item.SET1_VAL != condVal)||
				(condSeq == 2 && item.SET2_VAL != condVal)||
				(condSeq == 3 && item.SET3_VAL != condVal)||
				(condSeq == 4 && item.SET4_VAL != condVal)||
				(condSeq == 5 && item.SET5_VAL != condVal)
			 ) flag = false;
		}
		if(flag)
	    el.append($('<option>', { 
	        value: item.CMON_CD,
	        text : item.CMCD_NM,
	        set1 : item.SET1_VAL,
	        set2 : item.SET2_VAL,
	        set3 : item.SET3_VAL,
	        set4 : item.SET4_VAL,
	        set5 : item.SET5_VAL
	    }));
	});
	if(val) el.val(val).prop("selected", true);
}

/************************************************************************
* 함 수 명 : gfn_setValueAuto
* 기    능 : element 에 포맷에 맞게 값을 세팅
* 인    자 : input, str, data_format, default_value, initValYn
* 리 턴 값 : String
************************************************************************/
function gfn_setValueAuto(input, str, data_format, default_value, initValYn)
{
  var nm = input;
  nm = typeof nm == "string" ? nm : nm.attr("id");
  input = typeof input == "string" ? $("#" + input) : $(input);
  if ( input.length == 0 ) console.log("gfn_setValueAuto: input('id="+nm+"') is not defined...");
  if ( default_value != null && (str == null || str == "")) str = default_value;
  str = String(str);
  switch ( input.prop("tagName") )
  {
    case "SELECT": input.val(str).attr("selected", true); break;
    case "INPUT":
    {
      if (input.attr("type") == 'checkbox'){
        input.attr("checked", input.val() == str);
      }else if (input.attr("type") == 'radio'){
    	input.prop('checked', true);
      }else if ( input.attr("data_format") ){
    	input.val(gfn_formatValue(str, input.attr("data_format")));
      }else if ( data_format ){
    	input.val(gfn_formatValue(str, data_format));
      }else{
        input.val(str);
      }
    }
    break;
    case "SPAN": input.text(gfn_formatValue(str, data_format)); break;
    default: input.val(str); break;
  }
  if(initValYn == 'Y'){
	input.attr("initVal", str);
  }
}

/************************************************************************
 * 함 수 명 : gfn_getRowType
 * 기    능 : Input CRUD 체크
 * 인    자 : input, data_format
 * 리 턴 값 : String
 ************************************************************************/
function gfn_getRowType(input, data_format)
{
	var nm = input;
	nm = typeof nm == "string" ? nm : nm.attr("id");
	input = typeof input == "string" ? $("#" + input) : $(input);
	if ( input.length == 0 ) console.log("gfn_getRowType: input('id="+nm+"') is not defined...");
	var oldVal = input.attr("initVal");
	var newVal;
	switch ( input.prop("tagName") )
	{
		case "SELECT":  
		case "INPUT":
			newVal = gfn_getNormalValue(input, data_format || input.attr("data_format"));
		break;
		case "SPAN":
			newVal = input.text();
		break;
	}
	if (gfn_isNull(oldVal) && newVal != ''){
		return 'I';
	}else if ((gfn_isNull(oldVal) && newVal == '') || oldVal == newVal){
		return 'N';
	}else if (oldVal != '' && newVal == ''){
		return 'D';
	}else if (oldVal != newVal){
		return 'U';
	}else{
		return 'N';
	}
}

/************************************************************************
 * 함 수 명 : gfn_getRowTypeStr
 * 기    능 : Input CRUD 체크한 문자열 gfn_getRowTypeStr('NUUU')
 * 인    자 : str
 * 리 턴 값 : String
 ************************************************************************/
function gfn_getRowTypeStr(rowTypes)
{
	if ( rowTypes.indexOf('U') > -1 ) return 'U';
	else return rowTypes.substring(0,1);
}

/************************************************************************
* 함 수 명 : gfn_formatValue
* 기    능 : 문자열 포매팅 
* 인    자 : data_format
* 리 턴 값 : String
************************************************************************/
function gfn_formatValue(str, data_format, point_count)
{
  var rv = "";
  str = gfn_nvl(str,"").toString();
  switch ( data_format )
  {
    case "dfDateYy":  rv = gfn_formatValueMask(str, "####"); break;
    case "dfDateMm":  rv = gfn_formatValueMask(str, "##"); break;
    case "dfDateYmd": rv = gfn_formatValueMask(str, "####.##.##"); break;
    case "dfDateYm":  rv = gfn_formatValueMask(str, "####.##"); break;
    case "dfDateMd":  rv = gfn_formatValueMask(str, "##.##"); break;
    case "dfTimeHms": rv = gfn_formatValueMask(str, "##:##:##"); break;
    case "dfTimeHm":  rv = gfn_formatValueMask(str, "##:##"); break;
    case "dfTimeYmdhms":  rv = gfn_formatValueMask(str, "####.##.## ##:##:##"); break;
    case "dfIdNo":    rv = gfn_formatValueMask(str, "######-#######"); break;
    case "dfSaupNo":  rv = gfn_formatValueMask(str, "###-##-#####"); break;
    case "dfCardNo":  rv = gfn_formatValueMask(str, "####-####-####-####"); break;
    case "dfPostNo":  rv = gfn_formatValueMask(str, "###-###"); break;
    case "dfCorpNo":  rv = gfn_formatValueMask(str, "######-#######"); break;
    case "dfIssueNo": rv = gfn_formatValueMask(str, "####-######"); break;
    case "dfNo":      rv = str.replace(/\D/g, ""); break;
    case "dfInteger+":rv = gfn_formatComma(str.replace(/\D/g, "")); break;
    case "dfInteger1":rv = gfn_formatComma(str.replace(/\D/g, "")); break;
    case "dfInteger":
    {
      var sign = str.substr(0, 1) == "-" ? "-" : "";
      rv = sign + gfn_formatComma(str.replace(/\D/g, ""));
    }
    break;
    case "dfFloat+":
    {
      var pointidx = str.indexOf(".");
      var pointbelow = ( pointidx >= 0 ) ? "."+ str.substr(pointidx).replace(/\D/g, "") : "";
      var numvalue = gfn_formatComma(str.substr(0, pointidx >= 0 ? pointidx : str.length).replace(/\D/g, ""));
      if ( point_count != null)
      {
        numvalue = numvalue == "" ? "0" : numvalue;
        pointbelow = gfn_rPad(pointbelow || ".", "0", point_count+1);
      }
      rv = numvalue + pointbelow;
    }
    break;
    case "dfFloat":
    {
      var sign = str.substr(0, 1) == "-" ? "-" : "";
      var pointidx = str.indexOf(".");
      var pointbelow = ( pointidx >= 0 ) ? "."+str.substr(pointidx).replace(/\D/g, "") : "";
      var numvalue = gfn_formatComma(str.substr(0, pointidx >= 0 ? pointidx : str.length).replace(/\D/g, ""));
      if ( point_count != null)
      {
        numvalue = numvalue == "" ? "0" : numvalue;
        pointbelow = gfn_rPad(pointbelow || ".", "0", point_count+1);
      }
      rv = sign + (sign && pointbelow && ! numvalue ? "0" : numvalue) + pointbelow;
    }
    break;
    //case "dfEmail":
    default: rv = str; break;
  }
  return rv;
}

/************************************************************************
* 함 수 명 : gfn_formatValueMask
* 기    능 : 형식화된 숫자 문자열을 포매팅
* 인    자 : str, format
* 리 턴 값 : String
************************************************************************/
function gfn_formatValueMask(str, format)
{
  var rv = "";
  var numcount = gfn_countChr(format, '#');
  str = str.toString();
  str = str.replace(/\D/g, "").substr(0, numcount);
  var chrAt;
  var validx = 0;
  for ( var n = 0; n < format.length; n++ )
  {
    chrAt = format.charAt(n);
    rv += ( chrAt == '#' ) ? str.charAt(validx++) : chrAt;
    if ( validx >= str.length ) break;
  }
  return rv;
}


/************************************************************************
* 함 수 명 : gfn_countChr
* 기    능 : 문자열에서 chr 포함 갯수
* 인    자 : str, chr
* 리 턴 값 : int
************************************************************************/
function gfn_countChr(str, chr)
{
  var count = 0;
  var length = str.length;
  for ( var n = 0; n < length; n++ )
  {
    if ( chr == str.charAt(n) ) count++;
  }
  return count;
}

/************************************************************************
* 함 수 명 : gfn_countStr
* 기    능 : 문자열에서 str 포함 갯수
* 인    자 : text, str
* 리 턴 값 : int
************************************************************************/
function gfn_countStr(text, str)
{
  var count = 0;
  var start_index = 0;
  var str_length = str.length;
  if ( str_length == 0 ) return null;
  while ( true )
  {
    var find_index = text.indexOf(str, start_index);
    if ( find_index < 0 ) break;
    count++;
    start_index = find_index + str_length;
  }
  return count;
}

/************************************************************************
* 함 수 명 : gfn_formatComma
* 기    능 : 숫자 문자열에 ,(콤마)
* 인    자 : numstr
* 리 턴 값 : String
************************************************************************/
function gfn_formatComma(nStr)
{
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
	    x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	
	return x1 + x2;
}

/************************************************************************
* 함 수 명 : gfn_deletePrecedingZero
* 기    능 : 선행하는 0 제거
* 인    자 : numstr
* 리 턴 값 : String
************************************************************************/
function gfn_deletePrecedingZero(numstr)
{
  var replaced = numstr.replace(/^0+/, "");
  return numstr && ! replaced ? "0" : replaced;
}

/************************************************************************
* 함 수 명 : grn_getNormalValue
* 기    능 : element 의 포맷 안된 원데이터
* 인    자 : input, data_format
* 리 턴 값 : String
************************************************************************/
function gfn_getNormalValue(input, data_format)
{
  input = typeof input == "string" ? $("#" + input) : $(input);
  var rv = '';
  switch ( input.prop("tagName") )
  {
    case "SELECT": rv=(gfn_isNull(input.val()))?"":input.val(); break;
    case "INPUT": rv=input.val(); break;
    case "SPAN": rv=input.text(); break;
  }
  return gfn_restoreValue(rv, data_format || input.attr("data_format"));
}

/************************************************************************
 * 함 수 명 : gfn_restoreValue
 * 기    능 : 포맷 제거한 원 데이터
 * 인    자 : str, data_format
 * 리 턴 값 : String
 ************************************************************************/
function gfn_restoreValue(str, data_format)
{
  var rv = "";
  switch ( data_format )
  {
    case "dfDateYy":
    case "dfDateMm":
    case "dfDateYmd":
    case "dfDateYmd1":
    case "dfDateYm":
    case "dfDateMd":
    case "dfTimeHms":
    case "dfTimeHm":
    case "dfTimeYmdhms":
    case "dfIdNo":
    case "dfSaupNo":
    case "dfCardNo":
    case "dfPostNo":  rv = str.replace(/\D/g, ""); break;
    case "dfCorpNo":
    //case "dfNo":
    case "dfInteger+":
    case "dfInteger1":
    case "dfInteger":
    case "dfFloat+":
    case "dfFloat":   rv = str.replace(/,/g, ""); break;
    //case "dfEmail":
    default: rv = str; break;
  }
  return rv;
}

/************************************************************************
 * 함 수 명 : gfn_checkForm
 * 기    능 : 폼 Validation
 * 인    자 : form, 오류출력여부
 * 리 턴 값 : String
 ************************************************************************/
function gfn_checkForm(f, bJustCheck)
{
  f = typeof f == "string" ? document.forms[f] : f;
  var input;
  var rule;
  for ( var n = 0, sz = f.elements.length; n < sz; n++ )
  {
    input = f.elements[n];
    if ( ! input.name && ! input.id ) continue;
    switch ( input.type )
    {
      case "button":
      case "image":
      case "submit":
      case "reset": continue;
    }
    $input = $(input);
    if ( ! gfn_checkInput($input, bJustCheck) ) return false;
  }
  return true;
}

/************************************************************************
 * 함 수 명 : gfn_checkInput
 * 기    능 : input Validation
 * 인    자 : input, 오류출력여부
 * 리 턴 값 : String
 ************************************************************************/
function gfn_checkInput(input, bJustCheck)
{
  // 1. mand 필수여부
  // 2. minv, maxv
  // 4. minbl, maxbl
  // 5. email
  input = typeof input == "string" ? $(input) : input;
  var str = input.val()||input.value;
  var msg_prefix1 = input.attr('title') ? input.attr('title') +" 은(는) " : "";
  var msg_prefix2 = input.attr('title') ? input.attr('title') +" 을(를) " : "";
  
  if ( gfn_findArray(["Y", "true"],$(input).attr('mand')) > -1 && ! str )
  {
    if ( ! bJustCheck )
    {
      cmnAlert(msg_prefix2 + "입력해주세요.", null, function(){ gfn_input_selectfocus(input)});
    }
    return false;
  } else if (input.tagName == "SELECT" && str == '※'){
	if ( ! bJustCheck )
	{
	  cmnAlert(msg_prefix2 + "입력해주세요.", null, function(){ gfn_input_selectfocus(input)});
	}
	return false;
  }
  if ( str )
  {
    if ( $(input).attr('data_format') && ! gfn_checkFormatInput(input, true) ) return false;
    // 숫자 값 체크
    if ( $(input).attr('minv') && ! gfn_checkMinMaxVal(Number(gfn_getNormalValue(input)), Number($(input).attr('minv')), null) )
    {
      if ( ! bJustCheck )
      {
    	cmnAlert(msg_prefix2 + $(input).attr('minv') +" 이상으로 입력해주세요.", null, function(){ gfn_input_selectfocus(input)});
      }
      return false;
    }
    if ( $(input).attr('maxv') && ! gfn_checkMinMaxVal(Number(gfn_getNormalValue(input)), null, Number($(input).attr('maxv'))) )
    {
      if ( ! bJustCheck )
      {
    	cmnAlert(msg_prefix2 + $(input).attr('maxv') +" 이하로 입력해주세요.", null, function(){ gfn_input_selectfocus(input)});
      }
      return false;
    }
    // 문자길이 체크
    if ( $(input).attr('minbl') && ! gfn_checkMinMaxByteLen(str, Number($(input).attr('minbl')), null) )
    {
      if ( ! bJustCheck )
      {
    	cmnAlert(msg_prefix2 + $(input).attr('minbl') +"자 이상으로 입력해주세요.(현재 "+gfn_getByteLength(str)+"자)");
      }
      return false;
    }
    if ( $(input).attr('maxbl') && ! gfn_checkMinMaxByteLen(str, null, Number($(input).attr('maxbl'))) )
    {
      if ( ! bJustCheck )
      {
    	cmnAlert(msg_prefix2 + $(input).attr('maxbl') +"자 이하로 입력해주세요.(현재 "+gfn_getByteLength(str)+"자)", null, function(){ gfn_input_selectfocus(input)});
      }
      return false;
    }
  }
  return true;
}

/************************************************************************
 * 함 수 명 : gfn_checkFormatInput
 * 기    능 : input format check
 * 인    자 : element
 * 리 턴 값 : boolean
 ************************************************************************/
function gfn_checkFormatInput(input, bShowMsg)
{
  input = typeof input == "string" ? $(input) : input;
  var check = gfn_checkFormatValue(input.val(), $(input).attr('data_format'));
  if ( ! check && bShowMsg )
  {
    cmnAlert( (input.attr('title') ? input.attr('title')+" 은(는) " : "") + gfn_getFormatErrMsg($(input).attr('data_format')), null, function(){ gfn_input_selectfocus(input)});
  }
  return check;
}

/************************************************************************
 * 함 수 명 : gfn_checkFormatValue
 * 기    능 : input format check
 * 인    자 : element
 * 리 턴 값 : boolean
 ************************************************************************/
function gfn_checkFormatValue(str, data_format, bShowMsg)
{
  var numstr;
  var check = false;
  switch ( data_format )
  {
    case "dfDateYy":  check = /^\d{4}$/.test(str); break;
    case "dfDateMm":
    {
      check = /^\d{2}$/.test(str);
      if ( ! check ) break;
      var mm = Number(str.substr(0, 2));//-1 안함
      check = gfn_between(mm, 1, 12);
    }
    break;
    case "dfDateYmd":
    {
      check = /^\d{4}\.\d{2}\.\d{2}$/.test(str);
      if ( ! check ) break;
      numstr = str.replace(/\D/g, "");
      var yy = Number(numstr.substr(0, 4));
      var mm = Number(numstr.substr(4, 2))-1;
      var dd = Number(numstr.substr(6, 2));
      var dt = new Date(yy, mm, dd);
      check = yy == dt.getFullYear() && mm == dt.getMonth() && dd == dt.getDate();
    }
    break;
    case "dfDateYm":
    {
      check = /^\d{4}\.\d{2}$/.test(str);
      if ( ! check ) break;
      numstr = str.replace(/\D/g, "");
      var mm = Number(numstr.substr(4, 2));//-1 안함
      check = gfn_between(mm, 1, 12);
    }
    break;
    case "dfDateMd":
    {
      check = /^\d{2}\.\d{2}$/.test(str);
      if ( ! check ) break;
      numstr = str.replace(/\D/g, "");
      var mm = Number(numstr.substr(0, 2));//-1 안함
      var dd = Number(numstr.substr(2, 2));
      check = gfn_between(mm, 1, 12);
      if ( ! check ) break;
      return mm == 1 ? gfn_between(dd, 1, 31) :
             mm == 2 ? gfn_between(dd, 1, 29) :
             mm == 3 ? gfn_between(dd, 1, 31) :
             mm == 4 ? gfn_between(dd, 1, 30) :
             mm == 5 ? gfn_between(dd, 1, 31) :
             mm == 6 ? gfn_between(dd, 1, 30) :
             mm == 7 ? gfn_between(dd, 1, 31) :
             mm == 8 ? gfn_between(dd, 1, 31) :
             mm == 9 ? gfn_between(dd, 1, 30) :
             mm ==10 ? gfn_between(dd, 1, 31) :
             mm ==11 ? gfn_between(dd, 1, 30) :
             mm ==12 ? gfn_between(dd, 1, 31) : false;
    }
    //break;
    case "dfTimeHms":
    {
      check = /^\d{2}:\d{2}:\d{2}$/.test(str);
      if ( ! check ) break;
      numstr = str.replace(/\D/g, "");
      var hh = Number(numstr.substr(0, 2));
      var mi = Number(numstr.substr(2, 2));
      var ss = Number(numstr.substr(4, 2));
      check = gfn_between(hh, 0, 23) && gfn_between(mi, 0, 59) && gfn_between(ss, 0, 59);
    }
    break;
    case "dfTimeHm":
    {
      check = /^\d{2}:\d{2}$/.test(str);
      if ( ! check ) break;
      numstr = str.replace(/\D/g, "");
      var hh = Number(numstr.substr(0, 2));
      var mi = Number(numstr.substr(2, 2));
      check = gfn_between(hh, 0, 23) && gfn_between(mi, 0, 59);
    }
    break;
    case "dfTimeYmdhms":
    {
      check = /^\d{4}\/\d{2}\/\d{2}[ ]\d{2}:\d{2}:\d{2}$/.test(str);
      if ( ! check ) break;
      numstr = str.replace(/\D/g, "");
      var yy = Number(numstr.substr(0, 4));
      var mm = Number(numstr.substr(4, 2))-1;
      var dd = Number(numstr.substr(6, 2));
      var dt = new Date(yy, mm, dd);
      check = yy == dt.getFullYear() && mm == dt.getMonth() && dd == dt.getDate();
      if ( ! check ) break;
      var hh = Number(numstr.substr(8, 2));
      var mi = Number(numstr.substr(10, 2));
      var ss = Number(numstr.substr(12, 2));
      check = gfn_between(hh, 0, 23) && gfn_between(mi, 0, 59) && gfn_between(ss, 0, 59);
    }
    break;
    case "dfIdNo":
    {
      check = /^\d{6}-\d{7}$/.test(str);
      if ( ! check ) break;
      check = gfn_isRsrNo(gfn_restoreValue(str, data_format));
      if ( ! check )
      {
        cmnConfirm("주민번호 형식이 맞지 않습니다. 계속 진행하시겠습니까?", null, function(result){if(result) check = true});
      }
    }
    break;
    case "dfSaupNo":
    {
      check = /^\d{3}-\d{2}-\d{5}$/.test(str);
      if ( ! check ) break;
      check = gfn_isCompRegNo(gfn_restoreValue(str, data_format));
      if ( ! check )
      {
        cmnConfirm("사업자번호 형식이 맞지 않습니다. 계속 진행하시겠습니까?", null, function(result){if(result) check = true});
      }
    }
    break;
    case "dfCorpNo":  check = /^\d{6}-\d{7}$/.test(str); break;
    case "dfCardNo":  check = /^\d{4}-\d{4}-\d{4}-\d{4}$/.test(str); break;
    case "dfPostNo":  check = /^\d{3}-\d{3}$/.test(str); break;
    case "dfNo":      check = /^\d+$/.test(str); break;
    case "dfInteger+":check = /^0|([1-9]\d*)$/.test(gfn_restoreValue(str, data_format)); break;
    case "dfInteger": check = /^-?(0|([1-9]\d*))$/.test(gfn_restoreValue(str, data_format)); break;
    case "dfFloat+":  check = /^(0|([1-9]\d*))(\.\d*)?$/.test(gfn_restoreValue(str, data_format)); break;
    case "dfFloat":   check = /^-?(0|([1-9]\d*))(\.\d*)?$/.test(gfn_restoreValue(str, data_format)); break;
    case "dfEmail":   check = /^[^@]+@[^@]+$/.test(str);
    default:          check = true; break;
  }
  if ( ! check && bShowMsg )
  {
    cmnAlert(gfn_getFormatErrMsg(data_format));
  }
  return check;
}


/************************************************************************
 * 함 수 명 : gfn_getFormatErrMsg
 * 기    능 : 에러메시지
 * 인    자 : formatString
 * 리 턴 값 : msg
 ************************************************************************/
function gfn_getFormatErrMsg(data_format)
{
  var rv = "";
  switch ( data_format )
  {
    case "dfDateYy":  rv = "올바른 날짜(년도) 형식이 아닙니다.\n\n(2009) 형태로 입력해 주시기 바랍니다."; break;
    case "dfDateMm":  rv = "올바른 날짜(월) 형식이 아닙니다.\n\n(01) 형태로 입력해 주시기 바랍니다."; break;
    case "dfDateYmd": rv = "올바른 날짜(년월일) 형식이 아닙니다.\n\n(2009.01.01) 형태로 입력해 주시기 바랍니다."; break;
    case "dfDateYm":  rv = "올바른 날짜(년월) 형식이 아닙니다.\n\n(2009.01) 형태로 입력해 주시기 바랍니다."; break;
    case "dfDateMd":  rv = "올바른 날짜(월일) 형식이 아닙니다."; break;
    case "dfTimeHms": rv = "올바른 시간(시분초) 형식이 아닙니다."; break;
    case "dfTimeHm":  rv = "올바른 시간(시분) 형식이 아닙니다."; break;
    case "dfTimeYmdhms": rv = "올바른 시간(년월일시분초) 형식이 아닙니다."; break;
    case "dfIdNo":    rv = "올바른 주민번호 형식이 아닙니다."; break;
    case "dfSaupNo":  rv = "올바른 사업자번호 형식이 아닙니다."; break;
    case "dfCardNo":  rv = "올바른 카드번호 형식이 아닙니다."; break;
    case "dfPostNo":  rv = "올바른 우편번호 형식이 아닙니다."; break;
    case "dfCorpNo":  rv = "올바른 법인등록번호 형식이 아닙니다."; break;
    case "dfNo":      rv = "올바른 번호 형식이 아닙니다."; break;
    case "dfInteger+":rv = "올바른 숫자(양의 정수) 형식이 아닙니다."; break;
    case "dfInteger": rv = "올바른 숫자(정수) 형식이 아닙니다."; break;
    case "dfFloat+":  rv = "올바른 숫자(양의 실수) 형식이 아닙니다."; break;
    case "dfFloat":   rv = "올바른 숫자(실수) 형식이 아닙니다."; break;
    case "dfEmail":   rv = "올바른 이메일 형식이 아닙니다."; break;
    default:          rv = "올바른 형식이 아닙니다."; break;
  }
  return rv;
}

/************************************************************************
 * 함 수 명 : gfn_between
 * 기    능 : val 이 from, to 에 속하는지 체크
 * 인    자 : val, from, to
 * 리 턴 값 : bool
 ************************************************************************/
function gfn_between(val, from, to, not_include_from, not_include_to)
{
  if ( !(val >= from && val <= to) ) return false;
  if ( not_include_from && val == from ) return false;
  if ( not_include_to && val == to ) return false;
  return true;
}

/************************************************************************
 * 함 수 명 : gfn_checkMinMaxVal
 * 기    능 : 최소, 최대값 체크
 * 인    자 : v, min, max
 * 리 턴 값 : bool
 ************************************************************************/
function gfn_checkMinMaxVal(v, min, max)
{
  var check = true;
  if ( min != null ) check = v >= min;
  if ( ! check ) return false;
  if ( max != null ) check = v <= max;
  return check;
}

/************************************************************************
 * 함 수 명 : gfn_checkMinMaxByteLen
 * 기    능 : 최소, 최대 바이트 체크
 * 인    자 : str, min, max
 * 리 턴 값 : bool
 ************************************************************************/
function gfn_checkMinMaxByteLen(str, min, max)
{
  var v = gfn_getByteLength(str);
  return gfn_checkMinMaxVal(v, min, max);
}

/************************************************************************
 * 함 수 명 : gfn_getByteLength
 * 기    능 : 바이트 체크
 * 인    자 : str
 * 리 턴 값 : int
 ************************************************************************/
function gfn_getByteLength(str)
{
  var rv = 0;
  for ( var n = 0, sz = str.length; n < sz; n++ )
  {
    rv += str.charCodeAt(n) > 0x00ff ? 3 : 1;// utf-8 3바이트
  }
  return rv;
}

/************************************************************************
 * 함 수 명 : gfn_input_selectfocus
 * 기    능 : input focus
 * 인    자 : element
 * 리 턴 값 : null
 ************************************************************************/
function gfn_input_selectfocus(input)
{
  input = typeof input == "string" ? $("#" + input) : input;
  try { input.focus(); } catch(e) {}
}

/************************************************************************
 * 함 수 명 : gfn_checkFromTo
 * 기    능 : 시작일, 종료일 등 시작값이 종료값보다 작은지 체크
 * 인    자 : inputFrom
 * 리 턴 값 : boolean
 ************************************************************************/
function gfn_checkFromTo(inputFrom, inputTo)
{
  inputFrom = typeof inputFrom == "string" ? $("#" + inputFrom) : inputFrom;
  inputTo = typeof inputTo == "string" ? $("#" + inputTo) : inputTo;
  //console.log("inputFrom.value:"+inputFrom.value+",inputTo.value:"+inputTo.value);
  if ( inputFrom.val() && inputTo.val() )
  {
    if ( inputFrom.val() > inputTo.val() )
    {
      cmnAlert("입력일 확인바랍니다. ["+inputFrom.attr('title')+" < "+inputTo.attr('title')+"]");
      gfn_input_selectfocus(inputTo);
      return false;
    }
  }
  return true;
}

/************************************************************************
 * 함 수 명 : gfn_checkInputNN
 * 기    능 : input Not Null 체크
 * 인    자 : inputFrom
 * 리 턴 값 : boolean
 ************************************************************************/
function gfn_checkInputNN(input, bJustCheck)
{
  if(bJustCheck == null) bJustCheck = false;
  input = typeof input == "string" ? $("#"+input) : input;
  var str = input.val()||input.value;
  var msg_prefix1 = (input.attr('title') != null)? input.attr('title') +" 은(는) " : "";
  var msg_prefix2 = (input.attr('title') != null)? input.attr('title') +" 을(를) " : "";

  if ( gfn_isNull(str) || str == '※')
  {
    if ( ! bJustCheck )
    {
      cmnAlert(msg_prefix2 + "입력해주세요.");
      gfn_input_selectfocus(input);
    }
    return false;
  }
  return gfn_checkInput(input, bJustCheck);
}

/************************************************************************
 * 함 수 명 : rowspan
 * 기    능 : 테이블 row머지
 * 인    자 : 사용법 : $('#테이블 ID').rowspan(0,[0,1,2]);
 * 리 턴 값 : table row merge
 ************************************************************************/   
$.fn.rowspan = function(condIdx, colArr, isStats) {       
	return this.each(function(){      
		var that;     
		$('tr', this).each(function(row) {      
			if (!gfn_isNull(that) && $(this).find('td:eq('+condIdx+')').html() == $(that).find('td:eq('+condIdx+')').html()) {  
				var thisTr = $(this);
				$.each(colArr, function(idx, value) {
					rowspan = $(that).find('td:eq('+value+')').attr("rowspan") || 1;
					rowspan = Number(rowspan)+1;
					$(that).find('td:eq('+value+')').attr("rowspan", rowspan);
					thisTr.find('td:eq('+value+')').hide();
				});
			} else {            
				that = this;         
			}          
			that = (that == null) ? this : that;      
		});    
	});  
}; 

/************************************************************************
 * 함 수 명 : gfn_getUniqueBrchCode
 * 기    능 : 공통코드의 사업장코드중 중복 제거후 리턴
 * 인    자 : gfn_getUniqueBrchCode()
 * 리 턴 값 : json Array
 ************************************************************************/  
function gfn_getUniqueBrchCode(){
	var dsLocCd = gfn_getCmnCode("ITS_LOC_CD");
	var arrLocCd = new Array();
	
	$.each(dsLocCd, function (i, item) {
		if( getSearchJson(arrLocCd, item.SET1_VAL) == -1 ){
			arrLocCd.push(item); 
		}
	});
	return arrLocCd;
}

//json 검사
function getSearchJson(objJson, val){
	var bReturn = -1;
	jQuery.grep(objJson, function(obj, index) {
	    if( obj.SET1_VAL == val ){ 
	    	bReturn = index;
	    	return false;
	    }
	});
	return bReturn;
}

function custInfo(){
	var param = {
       "POPUP_URL": '<c:url value="/sample/test/custInfo.mvc"/>'
	};
  cmnPopup(param, null, function(result){
  });
}
