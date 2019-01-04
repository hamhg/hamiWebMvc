/***************************************************************************
 * Program ID    : stringUtils.js
 * Program Name  : String 관련함수
 * Author        : HHG
 * Created Date  : 2018.07.17
 *
 * =========================================================================
 * 수정일자     수정자    내    용
 * =========================================================================
 * 2017.05.17   나종식    최초작성
 **************************************************************************/

/***************************************************************************
 * gfn_isString           	: string 여부 반환
 * gfn_split           		: 특정 문자열을 기준으로 전체 문자열을 나누어서 하나의 배열(Array)로 만들어 반납하는 함수
 * gfn_replace	        	: 입력된 문자열의 일부분을 다른 문자열로 치환하는 함수
 * gfn_quote	          	: 입력된 문자열의 양쪽에 쌍따옴표를 붙여 반환
 * gfn_toString	      		: 입력값을 String으로 변경
 * gfn_subStr	        	: 지정한 위치에서 시작하고 지정한 길이를 갖는 부분 문자열을 반환하는 함수
 * gfn_length	        	: 입력값 형태에 따라서 길이 또는 범위를 구하는 함수
 * gfn_lengthByte	    	: 문자 전체 길이를 계산
 * gfn_lengthByteUtf8		: 입력받은 string 의 utf8단위의 byte를 리턴한다.
 * gfn_right	          	: 문자열의 오른쪽부분을 지정한 길이만큼 Return
 * gfn_left	          		: 문자열의 왼쪽부분을 지정한 길이만큼 Return
 * gfn_indexOf	        	: 전체 문자열 중 특정 문자열이 포함된 위치를 반납
 * gfn_trim	          		: 왼쪽, 오른쪽 문자열 삭제
 * gfn_lTrim	          	: 왼쪽에서 문자열 삭제
 * gfn_rTrim	          	: 오른쪽에서 문자열 삭제
 * gfn_lPad	          		: 자리수 만큼 왼쪽에 문자열 추가
 * gfn_rPad	          		: 자리수 만큼 오른쪽에 문자열 추가 
 * gfn_strToArr        		: String로 된 파라메타를 Array로 전환
 * gfn_arrToStr        		: Array를  파라메타용 String으로 전환
 * gfn_strToMap        		: String로 된 파라메타를 Array Map로 전환
 * gfn_mapToStr        		: Array Map을 파라메타용 String로 전환
 * gfn_dsToStr      		: Dataset을 파라메타용 String으로 전환
 * gfn_dsRowToStr      		: Dataset에서 한개의 Row를 파라메타용 String으로 전환 
 * gfn_pos                  : 문자열의 위치를 대소문자 구별하여 찾는다
 * gfn_posCase              : 문자열의 위치를 대소문자 구별없이 찾는다
 * gfn_posReverse           : 문자열의 위치를 대소문자 구별하여 거꾸로 찾는다
 * gfn_mid                  : 입력된 문자열에서 가운데 부분을 주어진 길이만큼 Return
 * gfn_midString            : 시작글자와 끝글자에 해당하는 글자의 사이에 있는 가운데 글자를 대소문자를 구별하여 찾는다.
 **************************************************************************/ 

/************************************************************************
* 함 수 명 : gfn_isString
* 기    능 : string 여부 반환
* 인    자 : value 확인할 value.
* 리 턴 값 : {boolean} string 여부.
*************************************************************************/ 
gfn_isString = function(value) 
{
	return typeof value === 'string';
};

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
};

/************************************************************************
* 함 수 명 : gfn_split
* 기    능 : 특정 문자열을 기준으로 전체 문자열을 나누어서 하나의 배열(Array)로 만들어 반납하는 함수
* 인    자 : strString - 원본 문자열
*             strChar - 나눌 기준이 되는 문자
* 리 턴 값 : 차원 배열
*************************************************************************/ 
gfn_split = function(strString, strChar)
{
	var rtnArr = new Array();
	var arrArgument = gfn_split.arguments;

	if (arrArgument.length < 1) {
	} else if (arrArgument.length < 2) {
		if (!gfn_isNull(arrArgument[0])) {
			rtnArr[0] = arrArgument[0];
		}
	} else if (arrArgument.length == 2) {
		if (!gfn_isNull(arrArgument[0])) {
			rtnArr = arrArgument[0].toString().split(arrArgument[1]);
		}
	} else {
		if (!gfn_isNull(arrArgument[0])) {
			rtnArr = arrArgument[0].toString().split(arrArgument[1]);
		}
	}
	
	return rtnArr;
};

/************************************************************************
* 함 수 명 : gfn_replace
* 기    능 : 입력된 문자열의 일부분을 다른 문자열로 치환하는 함수
* 인    자 : Val - 원본 문자열
*			  strOld - 원본 문자열에서 찾을 문자열
*             strNew - 새로 바꿀 문자열
* 리 턴 값 : String 문자열
*************************************************************************/ 
gfn_replace = function(Val, strOld, strNew)
{
	var varRtnValue = "";
	var arrArgument = gfn_replace.arguments;

	if (gfn_isNull(arrArgument[0]) || gfn_isNull(arrArgument[1])) 
	{
		return varRtnValue;
	}
	
	if (gfn_isNull(arrArgument[2])) {
		arrArgument[2] = "";
	}

	varRtnValue = arrArgument[0].toString().replace(arrArgument[1], arrArgument[2]);
	return varRtnValue;
};

/************************************************************************
* 함 수 명 : gfn_quote
* 기    능 : 입력된 문자열의 양쪽에 쌍따옴표를 붙여 반환
* 인    자 : Val - 문자열
* 리 턴 값 : String 양쪽에 쌍따옴표를 붙인 문자열
*************************************************************************/
gfn_quote = function()
{
	var retVal = '""';
	var arrArgument = gfn_quote.arguments;

	if ((arrArgument != null) && (arrArgument.length >= 1) && (!gfn_isNull(arrArgument[0]))) {
		retVal = nexacro.wrapQuote(new String(arrArgument[0]));
	}

	return retVal;
};

/************************************************************************
* 함 수 명 : gfn_toString
* 기    능 : 입력값을 String으로 변경
* 인    자 : Val - 문자열
* 리 턴 값 : String 문자열
*************************************************************************/
gfn_toString = function(Val)
{
	if (gfn_isNull(Val)) 
	{
		return new String();
	}
	return new String(Val);
};

/************************************************************************
* 함 수 명 : gfn_subStr
* 기    능 : 지정한 위치에서 시작하고 지정한 길이를 갖는 부분 문자열을 반환하는 함수
* 인    자 : strString - 가운데 부문을 얻어올 원본 문자열
*             nStart - 얻어올 첫 글자의 Index
*             nLength - Integer 얻어올 글자수 [Default length(해당 개채의 길이)]
* 리 턴 값 : String 문자열
*************************************************************************/
gfn_subStr = function(sVal, nStart, nLength)
{
	var retVal = "";
	if (!gfn_isNull(sVal)) {
		if (gfn_isNull(nLength)) {
			retVal = String(sVal).substr(nStart);
		} else {
			retVal = String(sVal).substr(nStart, nLength);
		}		
	}

	return retVal;
};

/************************************************************************
* 함 수 명 : gfn_length
* 기    능 : 입력값 형태에 따라서 길이 또는 범위를 구하는 함수
* 인    자 : Val - 문자열
* 리 턴 값 : Integer Type에 따라 구해진 길이
*************************************************************************/
gfn_length = function(Val)
{
	if(gfn_isNull(Val)) return 0;
 
 	if ( typeof(Val) == "object" ) 
 	{
		var rtnVal = 0;
		
		// Container
		if(Val.components)
		{
			rtnVal = Val.components.length;
		}
		// 배열일 경우
		else if(Val.constructor == Array) {
			rtnVal = Val.length;
		}
		// 캘린더 등
		else {
			rtnVal = String(Val).length;
		}
		
		return rtnVal;
 	} 
 	else {
		return Val.toString().length;//string, number인 경우
 	}
};

/************************************************************************
* 함 수 명 : gfn_lengthByte
* 기    능 : 문자 전체 길이를 계산
*             - 문자, 숫자, 특수문자 : 1 로 Count
*             - 그외 한글/한자 : 2 로 count 되어 합산 
* 인    자 : sVal - 문자열
* 리 턴 값 : Integer Type에 따라 구해진 길이
*************************************************************************/
gfn_lengthByte = function(sVal)
{
	var lengthByte = 0;

	if (gfn_isNull(sVal)) 
	{
		return 0;
	}
	
	sVal = sVal.toString();
	
	for (var i = 0; i < sVal.length; i++) 
	{
		if (sVal.charCodeAt(i) > 127) 
		{
			lengthByte += 2;
		}
		else 
		{
			lengthByte += 1;
		}
	}

	return lengthByte;
};

/************************************************************************
* 함 수 명 : gfn_lengthByteUtf8
* 기    능 : 입력받은 string 의 utf8단위의 byte를 리턴한다.
*             - 문자, 숫자, 특수문자 : 1 로 Count
*             - 그외 한글/한자 : 2 로 count 되어 합산 
* 인    자 : sValue - 문자열
* 리 턴 값 : Integer Type에 따라 구해진 길이
*************************************************************************/
gfn_lengthByteUtf8 = function(sValue)
{
    var v_ChkStr = sValue.toString();
    var v_cnt = 0;
    var charCode = "";
   
    for (var i=0; i<v_ChkStr.length; i++)
    {
        charCode = v_ChkStr.charCodeAt(i);

        if (charCode <= 0x00007F) {
            v_cnt += 1;
        } else if (charCode <= 0x0007FF) {
            v_cnt += 2;
        } else if (charCode <= 0x00FFFF) {
            v_cnt += 3;
        } else {
            v_cnt += 4;
        }
    }
	return v_cnt;
};

/************************************************************************
* 함 수 명 : gfn_right
* 기    능 : 문자열의 오른쪽부분을 지정한 길이만큼 Return
* 인    자 : Val - 오른 부분을 얻어올 원본 문자열
*            nSize - 얻어올 크기. [Default Value = 0]
* 리 턴 값 : String 문자열
*************************************************************************/
gfn_right = function(Val, nSize)
{
	var nStart = gfn_toString(Val).length;
	var nEnd = Number(nStart) - Number(nSize);
	var rtnVal = Val.substring(nStart, nEnd);

	return rtnVal;
};

/************************************************************************
* 함 수 명 : gfn_left
* 기    능 : 문자열의 왼쪽부분을 지정한 길이만큼 Return
* 인    자 : Val - 왼쪽 부분을 얻어올 원본 문자열
*            nSize - 얻어올 크기. [Default Value = 0]
* 리 턴 값 : String 문자열
*************************************************************************/
gfn_left = function(Val, nSize)
{
	return gfn_toString(Val).substr(0, nSize);
};

/************************************************************************
* 함 수 명 : gfn_indexOf
* 기    능 : 전체 문자열 중 특정 문자열이 포함된 위치를 반납
* 인    자 : Val    - 원본 문자열
*            strOld - 검사할 문자열
*            index  - 시작순서
* 리 턴 값 : Integer 문자열이 포함된 위치의 index값
*************************************************************************/
gfn_indexOf = function(Val, strOld, index)
{
	if (gfn_isNull(index)) 
	{
		index = 0;
	}
	return gfn_toString(Val).indexOf(strOld, index);
};

/************************************************************************
* 함 수 명 : gfn_trim
* 기    능 : 왼쪽, 오른쪽 문자열 삭제
* 인    자 : sOrg - 원래 문자열
*            sTrim - Trim할 문자열(옵션 : Default=" ") 
* 리 턴 값 : String Trim된 문자열
*************************************************************************/
gfn_trim = function(sOrg, sTrim)
{
	var rtnVal = "";
	if (gfn_isNull(sTrim)) 
	{
		sTrim = " ";
	}
	rtnVal = gfn_rTrim(sOrg, sTrim);
	rtnVal = gfn_lTrim(rtnVal, sTrim);

	return rtnVal;
};

/************************************************************************
* 함 수 명 : gfn_lTrim
* 기    능 : 왼쪽에서 문자열 삭제
* 인    자 : sOrg - 원래 문자열
*            sTrim - Trim할 문자열(옵션 : Default=" ") 
* 리 턴 값 : String Trim된 문자열
*************************************************************************/
gfn_lTrim = function(sOrg, sTrim)
{
	var chk,pos;

	sOrg = gfn_toString(sOrg);

	if (gfn_isNull(sOrg)) 
	{
		return "";
	}
	if (gfn_isNull(sTrim)) 
	{
		sTrim = " ";
	}

	for (pos = 0; pos < sOrg.length; pos += sTrim.length) 
	{
		if (sOrg.substr(pos, sTrim.length) != sTrim) 
		{
			break;
		}
	}

	return sOrg.substr(pos);
};

/************************************************************************
* 함 수 명 : gfn_rTrim
* 기    능 : 오른쪽에서 문자열 삭제
* 인    자 : sOrg - 원래 문자열
*            sTrim - Trim할 문자열(옵션 : Default=" ") 
* 리 턴 값 : String Trim된 문자열
*************************************************************************/
gfn_rTrim = function(sOrg, sTrim)
{
	var pos,nStart;

	sOrg = gfn_toString(sOrg);

	if (gfn_isNull(sOrg)) 
	{
		return "";
	}
	if (gfn_isNull(sTrim)) 
	{
		sTrim = " ";
	}

	for (pos = sOrg.length - sTrim.length; pos >= 0; pos -= sTrim.length) 
	{
		if (sOrg.substr(pos, sTrim.length) != sTrim) 
		{
			break;
		}
	}

	return sOrg.substr(0, pos + sTrim.length);
};

/************************************************************************
* 함 수 명 : gfn_lPad
* 기    능 : 자리수 만큼 왼쪽에 문자열 추가
* 인    자 : sOrg - 원래 문자열
*            sPad - Pad할 문자열
*            nCnt - 자리수
* 리 턴 값 : String Pad된 문자열
*************************************************************************/
gfn_lPad = function(sOrg, sPad, nCnt)
{
	var i,sRet = "";
	var nLength;

	if (gfn_isNull(sOrg)) 
	{
		return "";
	}
	if (gfn_isNull(sPad)) 
	{
		sPad = " ";
	}
	if (gfn_isNull(nCnt)) 
	{
		nCnt = 1;
	}

	for (i = 0; i < nCnt; i++) 
	{
		sRet += sPad;
	}
	sRet += sOrg;
	
	nLength = gfn_length(sOrg) > nCnt ? gfn_length(sOrg) : nCnt;

	return gfn_right(sRet,nLength);
};

/************************************************************************
* 함 수 명 : gfn_rPad
* 기    능 : 자리수 만큼 오른쪽에 문자열 추가
* 인    자 : sOrg - 원래 문자열
*            sPad - Pad할 문자열
*            nCnt - 자리수
* 리 턴 값 : String Pad된 문자열
*************************************************************************/
gfn_rPad = function(sOrg, sPad, nCnt)
{
	var i,sRet = "";
	var nLength;
	
	if (gfn_isNull(sOrg)) 
	{
		return "";
	}
	if (gfn_isNull(sPad)) 
	{
		sPad = " ";
	}
	if (gfn_isNull(nCnt)) 
	{
		nCnt = 1;
	}

	sRet += sOrg;
	for (i = 0; i < nCnt; i++) 
	{
		sRet += sPad;
	}

	nLength = gfn_length(sOrg) > nCnt ? gfn_length(sOrg) : nCnt;
	
	return gfn_left(sRet,nLength);
};

/************************************************************************
* 함 수 명 : gfn_strToArr
* 기    능 : String로 된 파라메타를 Array로 전환
* 인    자 : strParam - 원래 문자열
*            strDelimiter - 잘라낼 구분 문자열(default "^")
* 리 턴 값 : Array [value,value2...]
*************************************************************************/
gfn_strToArr = function (strParam, strDelimiter)
{
	if ( gfn_isNull(strDelimiter) ) strDelimiter = "^";

	//strParam = gfn_toString(strParam);

	var splitArr = strParam.split(strDelimiter);

	return splitArr;
};

/************************************************************************
* 함 수 명 : gfn_arrToStr
* 기    능 : String로 된 파라메타를 Array로 전환
* 인    자 : arrParam - 문자열Array
*            strDelimiter - 구분 문자열(default "^")
* 리 턴 값 : string "value^value2..."
*************************************************************************/
gfn_arrToStr = function (arrParam, strDelimiter)
{
	if ( gfn_isNull(strDelimiter) ) strDelimiter = "^";

	var strParam = "";
	for ( i=0; i<arrParam.length; i++ ) {
		if ( i > 0 ) strParam += strDelimiter;
		strParam += arrParam[i];
	}

	return strParam;
};

/************************************************************************
* 함 수 명 : gfn_strToMap
* 기    능 : String로 된 파라메타를 Array(map)로 전환
* 인    자 : strParam - 원래 문자열
*            strDelimiter1 - 첫번째로 잘라낼 구분 문자열(default "^")
*            strDelimiter2 - 두번째로 잘라낼 구분 문자열(default "=")
* 리 턴 값 : Array Map {key:value,ke2:value2...}
*************************************************************************/
gfn_strToMap = function (strParam, strDelimiter1, strDelimiter2)
{
	if ( gfn_isNull(strDelimiter1) ) strDelimiter1 = "^";
	if ( gfn_isNull(strDelimiter2) ) strDelimiter2 = "=";

	//strParam = gfn_toString(strParam);
	var strArr = "var arrParam = {";
	var splitArr1 = strParam.split(strDelimiter1);
	var splitArr2;
	for (var i = 0; i < splitArr1.length; i++) 
	{
		if ( i > 0 ) strArr += ",";

		splitArr2 = splitArr1[i].split(strDelimiter2);
		for ( var j = 0; j < splitArr2.length; j++ ) {
			if ( j == 0 ) {
				strArr += splitArr2[j];
			} else {
				strArr += ":'" + splitArr2[j] + "'";
			}
		}
	}
	strArr += "};";
	eval(strArr);	
	return arrParam;
};

/************************************************************************
* 함 수 명 : gfn_mapToStr
* 기    능 : Array(map)로 된 파라메타를 String로 전환
* 인    자 : arrParam - Array Map
*            strDelimiter1 - 첫번째로 잘라낼 구분 문자열(default "^")
*            strDelimiter2 - 두번째로 잘라낼 구분 문자열(default "=")
* 리 턴 값 : String "key=value^ke2=value2..."
*************************************************************************/
gfn_mapToStr = function (arrParam, strDelimiter1, strDelimiter2)
{
	if ( gfn_isNull(strDelimiter1) ) strDelimiter1 = "^";
	if ( gfn_isNull(strDelimiter2) ) strDelimiter2 = "=";

	var strArrMap = "";
	var nCnt = 0;
	for ( key in arrParam ) {
		if ( nCnt > 0 ) strArrMap += strDelimiter1;
		
		strArrMap += key + strDelimiter2 + arrParam[key];

		nCnt++;
	}

	return strArrMap;
};

/************************************************************************
* 함 수 명 : gfn_pos
* 기    능 : 문자열의 위치를 대소문자 구별하여 찾는다
* 인    자 : sOrg   - 원래 문자열
*            sFind  - 찾고자 하는 문자열
*            nStart - 검색 시작위치 (옵션 : Default=0) 
* 리 턴 값 : Integer 찾고자 하는 문자열의 시작위치
*************************************************************************/
gfn_pos = function(sOrg, sFind, nStart)
{
	if (gfn_isNull(sOrg) || gfn_isNull(sFind)) 
	{
		return -1;
	}
	if (gfn_isNull(nStart)) 
	{
		nStart = 0;
	}

	return sOrg.indexOf(sFind, nStart);
};

/************************************************************************
* 함 수 명 : gfn_posCase
* 기    능 : 문자열의 위치를 대소문자 구별없이 찾는다
* 인    자 : sOrg   - 원래 문자열
*            sFind  - 찾고자 하는 문자열
*            nStart - 검색 시작위치 (옵션 : Default=0)
* 리 턴 값 : Integer 찾고자 하는 문자열의 시작위치
*************************************************************************/
gfn_posCase = function(sOrg, sFind, nStart)
{
	if (gfn_isNull(sOrg) || gfn_isNull(sFind)) 
	{
		return -1;
	}
	if (gfn_isNull(nStart)) 
	{
		nStart = 0;
	}

	return sOrg.toLowerCase().indexOf(sFind.toLowerCase(), nStart);
};

/************************************************************************
* 함 수 명 : gfn_posReverse
* 기    능 : 문자열의 위치를 대소문자 구별하여 거꾸로 찾는다
* 인    자 : sOrg   - 원래 문자열
*            sFind  - 찾고자 하는 문자열( 예 : "bb" )
*            nStart - 검색 시작위치 (옵션 : Default=문자열의 끝 )
* 리 턴 값 : Integer 찾고자 하는 문자열의 시작위치
*************************************************************************/
gfn_posReverse = function(sOrg, sFind, nStart)
{
	var pos;

	if (gfn_isNull(sOrg) || gfn_isNull(sFind)) 
	{
		return -1;
	}
	if (gfn_isNull(nStart)) 
	{
		nStart = sOrg.length - 1;
	}

	for (pos = nStart; pos >= 0; pos--) 
	{
		if (sOrg.substr(pos, sFind.length) == sFind) 
		{
			break;
		}
	}

	return pos;
};

/************************************************************************
* 함 수 명 : gfn_mid
* 기    능 : 입력된 문자열에서 가운데 부분을 주어진 길이만큼 Return
* 인    자 : strString   - 가운데 부문을 얻어올 원본 문자열
*            nIndex - 얻어올 첫 글자의 Index
*            nSize   - 얻어올 글자수
* 리 턴 값 : String 오른쪽 부분이 얻어진 문자열
*************************************************************************/
gfn_mid = function(strString, nIndex, nSize)                     
{
    var nStart = ((nIndex == null) ? 0 : ((nIndex.toString().length <= 0) ? 0 : nIndex-1));                                      
    var nEnd = ((nSize == null) ? strString.toString().length : ((nSize.toString().length <= 0) ? strString.toString().length : nSize));                                                           
    var rtnVal = strString.substr(nStart, nEnd);               
 
    return String(rtnVal);                                     
};

/************************************************************************
* 함 수 명 : gfn_midString
* 기    능 : 시작글자와 끝글자에 해당하는 글자의 사이에 있는 가운데 글자를 대소문자를 구별하여 찾는다.
*             ( 예 : aaBBbbccdd에서 bb, dd사이의 글자 cc를 찾는다 )
* 인    자 : sOrg   - 원래 문자열
*            sStart - 찾고자 하는 시작 문자열(옵션 : Default = "")
*            sEnd   - 찾고자 하는 끝 문자열 (옵션 : Default = "")
*            nStart - 검색 시작위치 (옵션 : Default=0)
* 리 턴 값 : String 가운데 글자
*************************************************************************/
gfn_midString = function(sOrg, sStart, sEnd, nStart)
{
	var pos_start,pos_end,ret_str;

	if (gfn_isNull(sOrg)) 
	{
		return "";
	}
	if (gfn_isNull(sStart)) 
	{
		sStart = "";
	}
	if (gfn_isNull(sEnd)) 
	{
		sEnd = "";
	}
	if (gfn_isNull(nStart)) 
	{
		nStart = 0;
	}

	if (sStart == "") 
	{
		pos_start = nStart;
	}
	else 
	{
		pos_start = gfn_pos(sOrg, sStart, nStart);
		if (pos_start < 0) 
		{
			return "";
		}
	}
	if (sEnd == "") 
	{
		pos_end = sOrg.length;
	}
	else 
	{
		pos_end = gfn_pos(sOrg, sEnd, pos_start + sStart.length, nStart);
		if (pos_end < 0) 
		{
			return "";
		}
	}

	return sOrg.substring(pos_start + sStart.length, pos_end);
};

function delChar(str, ch) {
	/*
	 * ********************************************************************************************************
	 * 함수설명 : 문자열에서 특정문자를 제거한 새로운 문자열을 만든다. str : 문자열 ch : 제거할 문자
	 * **********************************************************************************************************
	 */
	var len = str.length;
	var ret = "";

	// 문자열에서 ch 문자를 제거한다. 예) , - 등등
	for (i = 0; i < len; ++i) {
		if (str.substring(i, i + 1) != ch)
			ret = ret + str.substring(i, i + 1);
	}

	return ret;
}

function replace(str, oldChar, newChar) {
	/*
	 * ********************************************************************************************************
	 * 함수설명 : 문자열에서 특정문자를 다른 문자로 치환한 새로운 문자열을 만든다. str : 문자열 oldChar : 바꾸기 전의 문자
	 * newChar : 바꿔서 넣을 문자
	 * **********************************************************************************************************
	 */
	var oldstr = "";

	while (oldstr != str) {
		oldstr = str;
		str = str.replace(oldChar, newChar);
	}

	return str;
}

function lTrim(str) {
	/*
	 * ********************************************************************************************************
	 * 함수설명 : 문자열에서 왼쪽의 공백을 제거한다. str : 문자열
	 * **********************************************************************************************************
	 */
	var i;
	i = 0;
	while (str.substring(i, i + 1) == ' ' || str.substring(i, i + 1) == '　')
		i = i + 1;
	return str.substring(i);
}

function rTrim(str) {
	/*
	 * ********************************************************************************************************
	 * 함수설명 : 문자열에서 오른쪽의 공백을 제거한다. str : 문자열
	 * **********************************************************************************************************
	 */

	var i = str.length - 1;
	while (i >= 0
			&& (str.substring(i, i + 1) == ' ' || str.substring(i, i + 1) == '　'))
		i = i - 1;
	return str.substring(0, i + 1);
}

function trim(str) {
	/*
	 * ********************************************************************************************************
	 * 함수설명 : 문자열에서 양쪽의 공백을 제거한다. str : 문자열
	 * **********************************************************************************************************
	 */
	if (str == "" || str.length == 0) {
		return str;
	} else {
		return (lTrim(rTrim(str)));
	}
}

function isEmpty(str) {
	/*
	 * ********************************************************************************************************
	 * 함수설명 : 문자열이 빈문자열 혹은 공백만 있는 문자열이지 검사한다. str : 문자열
	 * **********************************************************************************************************
	 */
	if (trim(str) == '')
		return true;
	return false;

}

function isContains(str, ch) {
	/*
	 * ********************************************************************************************************
	 * 함수설명: 문자열이 특정문자열을 포함하고 있는지 체크한다. str : 특정문자 포함여부를 체크할 대상 문자열 ch : 지정된
	 * 특정문자
	 * 
	 * **********************************************************************************************************
	 */
	var i = 0;
	for (i = 0; i < str.length; i++) {
		if (str.charAt(i) == ch)
			return true;
	}
	return false;
}

function isContainsOnly(str, chars) {
	/*
	 * ********************************************************************************************************
	 * 함수설명 : 해당문자열이 지정된 문자들만을 포함하고 있는지 검사한다. str : 검사할 문자열 chars : 지정된 문자들의 나열
	 * **********************************************************************************************************
	 */
	for (var inx = 0; inx < str.length; inx++) {
		if (chars.indexOf(str.charAt(inx)) == -1)
			return false;
	}
	return true;
}

function isUnderMaxLen(strName, str, maxLen) {
	/*
	 * ********************************************************************************************************
	 * 함수설명: 문자열의 글자수체크를 한다. checkInputLength 등의 함수 내에서 호출된다. StrName : 글자수 체크를
	 * 할 문자열의 한글명칭. 에러 메시지 출력 시에 사용한다. str : 글자 수 체크를 할 문자열 maxLen : 해당 필드의
	 * 최대글자수 (한글2,영문1)
	 * 
	 * **********************************************************************************************************
	 */
	var i, len = 0;
	var korLen = maxLen / 2;

	for (i = 0; i < str.length; i++)
		(str.charCodeAt(i) > 255) ? len += 2 : len++;
	if (maxLen < len) {
		alert(strName + "은(는) 영문(숫자)" + maxLen + "자, 한글" + korLen
				+ "자까지만 가능합니다. 현재 글자수(영문기준) : " + len);
		return false;
	}
	return true;
}

function isValidDay(year, month, day) {
	/*
	 * ********************************************************************************************************
	 * 함수설명 : 지정하는 년,월,일이 달력상으로 존재하는 날짜인지 검사한다. year : 년 month : 월 day : 일
	 * **********************************************************************************************************
	 */
	var m = parseInt(month, 10) - 1;
	var d = parseInt(day, 10);

	var end = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
		end[1] = 29;
	}

	return (d >= 1 && d <= end[m]);
}

function checkInputNumber(fieldCalledName, field) {
	/*
	 * ***************************************************************************************************
	 * 함수설명: 입력란에 숫자만이 입력되는지 체크한다.
	 * 
	 * fieldCalledName : 글자수 체크를 할 입력필드의 한글명칭. 에러 메시지 출력 시에 사용한다. field : html에서
	 * name으로 지정된 입력필드 객체
	 * 
	 * 사용예
	 * ***************************************************************************************************
	 */
	if (field.value != "") {
		if (!isContainsOnly(field.value, "0123456789")) {
			alert(fieldCalledName + "은(는) 숫자외의 문자열을 입력할 수 없습니다.");
			field.focus();
			field.value = field.value.substring(0, field.value.length - 1);
			return false;
		}
	}
	return true;
}

function checkNumber(fieldCalledName, field, min, max) {
	/*
	 * ***************************************************************************************************
	 * 함수설명: 입력란에 최종적으로 적합한 범위내의 숫자가 들어왔는지 검사한다.
	 * 
	 * fieldCalledName : 글자수 체크를 할 입력필드의 한글명칭. 에러 메시지 출력 시에 사용한다. field : html에서
	 * name으로 지정된 입력필드 객체 min : 최소값 max : 최대값
	 * 
	 * 사용예
	 * ***************************************************************************************************
	 */

	field.value = trim(field.value);
	if (!checkInputNumber(fieldCalledName, field)) {
		return false;
	}

	var fieldNumber = parseInt(field.value);
	if (!(fieldNumber >= min && fieldNumber <= max)) {
		alert(fieldCalledName + "의 값이" + parseInt(field.value) + "로 [" + min
				+ " ~ " + max + "] 사이의 범위를 벗어나 있습니다.");
		field.focus();
		return false;
	}
	return true;
}

/*
 * ********************************************************************************************************
 * 함수설명 : 문자열에서 문자간의 공백을 제거한다. str : 문자열
 * **********************************************************************************************************
 */
function PerfactTrim(val) {
	var rtnVal = "";
	var len = val.length;

	for (var i = 0; i < len; i++) {
		if (val.substring(i, i + 1) != " ") {
			rtnVal = rtnVal + val.substring(i, i + 1);
		}
	}

	return rtnVal;
}

