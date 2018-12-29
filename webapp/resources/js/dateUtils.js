/***************************************************************************
 * Program ID    : dateUtils.xjs
 * Program Name  : Date 관련함수
 * Author        : HHG
 * Created Date  : 2017.07.17
 *
 * =========================================================================
 * 수정일자     수정자    내    용
 * =========================================================================
 * 2017.05.17   나종식    최초작성
 **************************************************************************/

/***************************************************************************
 * gfn_today				: 해당 PC의 오늘 날짜를 가져온다. (YYYYMMDD)
 * gfn_todayTime			: 해당 PC의 오늘 날짜와 시간를 가져온다. (YYYYMMDDHHMMSS)
 * gfn_todayTimeMilisec		: 해당 PC의 오늘 날짜와 시간를 가져온다. (YYYYMMDDHHMMSSTTT)(1/1000초 3자리 포함)
 * gfn_todaySvr				: 서버날짜를 가져온다.
 * gfn_isStringDate			: 날짜에 대한 형식 체크
 * gfn_isStringDate6		: 6자리 년월 날짜에 대한 형식 체크
 * gfn_isTime				: 시간 정합성 체크
 * gfn_dateToStr			: Date타입의 날짜를 문자타입의 날짜로 변환하는 함수
 * gfn_strToDate			: String 타입의 형식을 날짜형식으로 변환
 * gfn_getLastDate			: 해당월의 마지막 날짜를 yyyyMMdd형태로 구하기
 * gfn_getLastDateNum		: 해당월의 마지막 날짜를 숫자로 구하기
 * gfn_addDate				: 입력된 날자에 OffSet 으로 지정된 만큼의 일을 더한다.
 * gfn_addMonth				: 입력된 날자에 OffSet 으로 지정된 만큼의 달을 더한다.
 * gfn_getDiffDay			: 2개의 날짜간의 Day count
 * gfn_getDiffMonth			: 두 월간의 차이 월수 계산
 * gfn_getDiffTime			: 두 시간간의 차이 계산
 * gfn_getDay				: 입력된 날자로부터 요일을 구함
 * gfn_getDayName			: 입력된 날자로부터 요일명을 구함
 * gfn_dateTime				: 년,월,일을 숫자로 받아서 YYYYMMDD형태로 변환( or YYYYMMDDHHMISS를 변환) 
 * gfn_getMaskFormatString  : 주어진 날짜 객체의 Mask Format 처리된 문자열을 반환.
 * gfn_getWeekOfYear        : 형태의 날짜를 입력하면 해당연도의 날짜에 해당하는 주차반환.
 * gfn_getFirstDate         : 전달된 月의 1일 만들기.
 * gfn_getThisMonthBegin    : 금월의 시작일자를 문자열 형태로 리턴한다.(PC일자)
 * gfn_getThisWeekBegin     : 금주의 시작일자를 문자열 형태로 리턴한다.(PC일자)
 * gfn_getYear     			: 해당년도 구하기
 * gfn_getYearMonth         : 해당년월 구하기
 * gfn_getMonth             : 해당월 구하기
 * gfn_getStartOfWeek       : 해당일자의  주 시작일자를 계산
 * gfn_getEndOfWeek         : 해당일자의  주 마지막일자를 계산
 * gfn_secToTime            : 초형식을 시간형식으로 변환
 * gfn_isLeapYear           : 윤년여부 확인 
 **************************************************************************/

//요일명칭 정의
//getMaskFormatString 함수에서 masking 할때 사용하는 명칭이다
weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
//weekName = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];

//축약 요일명칭 정의
//getMaskFormatString 함수에서 masking 할때 사용하는 명칭이다
weekShortName = ["일", "월", "화", "수", "목", "금", "토"];
//weekShortName = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];

//월명칭 정의
//getMaskFormatString 함수에서 masking 할때 사용하는 명칭이다
monthName = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];
//monthName =["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

//축약 월명칭 정의
//getMaskFormatString 함수에서 masking 할때 사용하는 명칭이다
monthShortName = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];
//monthShortName = ["Jan ", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

//오전/오후
//getMaskFormatString 함수에서 masking 할때 사용하는 명칭이다
ttName = ["오전", "오후"];
//ttName = ["AM", "PM"];

//mask format cache.
_dateMaskCache = {};
 
/************************************************************************
* 함 수 명 : gfn_today
* 기    능 : 해당 PC의 오늘 날짜를 가져온다.
* 인    자 : 
* 리 턴 값 : string 오늘 날짜 (YYYYMMDD)
*************************************************************************/ 
function gfn_today(delimiter)
{
	var strToday = "";
	var objDate = new Date();

	var strToday = objDate.getFullYear().toString() + delimiter;
	strToday += gfn_right("0" + (objDate.getMonth() + 1), 2) + delimiter;
	strToday += gfn_right("0" + objDate.getDate(), 2);

	return strToday;
}

/************************************************************************
* 함 수 명 : gfn_todayTime
* 기    능 : 해당 PC의 오늘 날짜와 시간를 가져온다.
* 인    자 : 
* 리 턴 값 : string 오늘 날짜+시간 (YYYYMMDDHHMMSS)
*************************************************************************/  
gfn_todayTime = function()
{
	var strToday = "";
	var objDate = new Date();
	var sToday = objDate.getFullYear().toString();
	sToday += gfn_right("0" + (objDate.getMonth() + 1), 2);
	sToday += gfn_right("0" + objDate.getDate(), 2);
	sToday += gfn_right("0" + objDate.getHours(), 2);
	sToday += gfn_right("0" + objDate.getMinutes(), 2);
	sToday += gfn_right("0" + objDate.getSeconds(), 2);
	// strToday += objDate.getMilliseconds();
	return sToday;
}

/************************************************************************
* 함 수 명 : gfn_todayTimeMilisec
* 기    능 : 해당 PC의 오늘 날짜와 시간를 가져온다.(1/1000초 3자리 포함)
* 인    자 : 
* 리 턴 값 : string 오늘 날짜+시간 (YYYYMMDDHHMMSS + 1/1000초 3자리)
*************************************************************************/  
gfn_todayTimeMilisec = function()
{
	var strToday = "";
	var objDate = new Date();
	var sToday = objDate.getFullYear().toString();
	sToday += gfn_right("0" + (objDate.getMonth() + 1), 2);
	sToday += gfn_right("0" + objDate.getDate(), 2);
	sToday += gfn_right("0" + objDate.getHours(), 2);
	sToday += gfn_right("0" + objDate.getMinutes(), 2);
	sToday += gfn_right("0" + objDate.getSeconds(), 2);
	sToday += gfn_right("0" + objDate.getMilliseconds(), 3);
	// strToday += objDate.getMilliseconds();
	return sToday;
}

/************************************************************************
* 함 수 명 : gfn_todaySvr
* 기    능 : 서버날짜를 가져온다.
* 인    자 : 
* 리 턴 값 : string 현재 날짜
*************************************************************************/ 
gfn_todaySvr = function()
{
	//임시로 PC날짜 Setup
	return gfn_today();
}

/************************************************************************
* 함 수 명 : gfn_isDate
* 기    능 : value의 Date 여부 반환.
* 인    자 : {date} objValue 확인할 value.
* 리 턴 값 : {boolean} Date 여부.
*************************************************************************/ 
gfn_isDate = function(objValue) 
{
	if ( gfn_isDateType(objValue) ) return true;

	return gfn_isStringDate(objValue);
}

/************************************************************************
* 함 수 명 : gfn_isDateType
* 기    능 : value의 Date Type 여부 반환.
* 인    자 : {date} objValue 확인할 value.
* 리 턴 값 : {boolean} Date 여부.
*************************************************************************/ 
gfn_isDateType = function(objValue) 
{
	if ( typeof objValue == 'object') {
		try	
		{
			var nDay = objValue.getDay();
			return true;
		} catch (e) {
			return false;
		}
	} 
}
		
/************************************************************************
* 함 수 명 : gfn_isStringDate
* 기    능 : 날짜에 대한 형식 체크
* 인    자 : sDate  - string 일자(yyyy-MM-dd 등)
* 리 턴 값 : boolean
*************************************************************************/ 
gfn_isStringDate = function(sDate)
{
	var stringWrapper = new String(sDate);
 	stringWrapper = stringWrapper.replace("/","").replace("/","");
 	stringWrapper = stringWrapper.replace("-","").replace("-","");
 	stringWrapper = stringWrapper.replace(".","").replace(".","");
	
	if( stringWrapper.length != 8 ) {
		return false;
	}
	
	var iMonth  = nexacro.toNumber(stringWrapper.substr(4,2));
	var iDate   = nexacro.toNumber(stringWrapper.substr(6,2));
	if( iMonth < 1 || iMonth > 12 ) {
		return false;	
	}
	if( iDate < 1 || iDate > gfn_getLastDateNum(stringWrapper) ) {
		return false;	
	}
	
	return true;
}

/************************************************************************
* 함 수 명 : gfn_isStringDate6
* 기    능 : 6자리 년월 날짜에 대한 형식 체크
* 인    자 : sDate  - string 일자(yyyyMM)
* 리 턴 값 : boolean
*************************************************************************/ 
gfn_isStringDate6 = function(sDate)
{
	if (gfn_length(sDate) != 6) 
	{
		return false;
	}
	else if (!gfn_isStringDate(sDate + "01")) 
	{
		return false;
	}
	return true;
}


/************************************************************************
* 함 수 명 : gfn_isTime
* 기    능 : 시간 정합성 체크
* 인    자 : val - string 입력문자열(시분초 6자리)
* 리 턴 값 : boolean
*************************************************************************/ 
gfn_isTime = function(val) 
{
    if (gfn_isDigit(val) != true) {
        return false;
    }
    
    if (val.trim().toString().length != 6) {
        return false;
    }
    
    var nHH = toNumber(val.toString().substr(0, 2));
    var nMM = toNumber(val.toString().substr(2, 2));
    var nSS = toNumber(val.toString().substr(4, 2));
    
    if(gfn_isNull(nHH) || gfn_isNull(nMM) || gfn_isNull(nMM)) {
        return false;
    }
    
    if(nHH > 23 || nHH < 0) {
        return false;
    }
    
    if(nMM > 59 || nMM < 0) {
        return false;
    }
    
    if(nSS > 59 || nSS < 0) {
        return false;
    }
    
    return true;
}

/************************************************************************
* 함 수 명 : gfn_dateToStr
* 기    능 : Date타입의 날짜를 문자타입의 날짜로 변환하는 함수
* 인    자 : date Date타입의 날짜
* 리 턴 값 : 문자타입의 날짜
*************************************************************************/ 
gfn_dateToStr = function (date)
{
	if ( typeof date == 'string' ) {
		return date;
	}

	var objDate = new Date(date);
	
	var strDate = objDate.getFullYear().toString();
	strDate += gfn_lPad(objDate.getMonth(), "0", 2);
	strDate += gfn_lPad(objDate.getDate(), "0", 2);
	return strDate;
}

/************************************************************************
* 함 수 명 : gfn_strToDate
* 기    능 : String 타입의 형식을 날짜형식으로 변환
* 인    자 : sDate  - 일자(yyyyMMdd)
* 리 턴 값 : Date 날짜
*************************************************************************/ 
gfn_strToDate = function(sValue)
{
	if ( gfn_isDateType(sValue) ) return sValue;
	
	var dt = new Date();
	if ( sValue.length == 4 ) //yyyy
	{
		dt.setFullYear(parseInt(sValue), 0, 0);
		dt.setHours(0, 0, 0);
		dt.setMilliseconds(0);
	}
	else if ( sValue.length == 6 ) //yyyyMM
	{
		dt.setFullYear(parseInt(sValue.substr(0,4)), parseInt(sValue.substr(4,2))-1, 0);
		dt.setHours(0, 0, 0);
		dt.setMilliseconds(0);
	}
	else if ( sValue.length == 8 ) //yyyyMMdd
	{
		dt.setFullYear(parseInt(sValue.substr(0,4)), parseInt(sValue.substr(4,2))-1, parseInt(sValue.substr(6,2)));
		dt.setHours(0, 0, 0);
		dt.setMilliseconds(0);
	}
	else if ( sValue.length == 10 ) //yyyyMMddhh
	{
		dt.setFullYear(parseInt(sValue.substr(0,4)), parseInt(sValue.substr(4,2))-1, parseInt(sValue.substr(6,2)));
		dt.setHours(parseInt(sValue.substr(8,2)), 0, 0);
		dt.setMilliseconds(0);
	}
	else if ( sValue.length == 12 )//yyyyMMddhhmm
	{
		dt.setFullYear(parseInt(sValue.substr(0,4)), parseInt(sValue.substr(4,2))-1, parseInt(sValue.substr(6,2)));
		dt.setHours(parseInt(sValue.substr(8,2)), parseInt(sValue.substr(10,2)), 0);
		dt.setMilliseconds(0);
	}
	else if ( sValue.length == 14 ) //yyyyMMddhhmmss
	{
		dt.setFullYear(parseInt(sValue.substr(0,4)), parseInt(sValue.substr(4,2))-1, parseInt(sValue.substr(6,2)));
		dt.setHours(parseInt(sValue.substr(8,2)), parseInt(sValue.substr(10,2)), parseInt(sValue.substr(12,2)));
		dt.setMilliseconds(0);
	}
	else if ( sValue.length == 19 ) //yyyy-MM-dd hh:mm:ss
	{
		dt.setFullYear(parseInt(sValue.substr(0,4)), parseInt(sValue.substr(5,2))-1, parseInt(sValue.substr(8,2)));
		dt.setHours(parseInt(sValue.substr(11,2)), parseInt(sValue.substr(14,2)), parseInt(sValue.substr(17,2)));
		dt.setMilliseconds(0);
	}	
	return dt;
}

/************************************************************************
* 함 수 명 : gfn_getLastDate
* 기    능 : 해당월의 마지막 날짜를 yyyyMMdd형태로 구하기
* 인    자 : sDate  - 일자(yyyyMMdd) 
* 리 턴 값 : string yyyyMMdd형태의 마지막 날짜 ( 예 : "20121130" )
*************************************************************************/ 
gfn_getLastDate = function(sDate)
{
	if (gfn_isNull(sDate)) 
	{
		sDate = gfn_today();
	}

	var nLastDate = gfn_getLastDateNum(sDate);

	return sDate.substr(0, 6) + nLastDate.toString();
}

/************************************************************************
* 함 수 명 : gfn_getLastDateNum
* 기    능 : 해당월의 마지막 날짜를 숫자로 구하기
* 인    자 : sDate  - 일자(yyyyMMdd) 
* 리 턴 값 : integer 마지막 날짜 숫자값 ( 예 : "30" )
*************************************************************************/ 
gfn_getLastDateNum = function(sDate)
{
	var nMonth,nLastDate;

	if (gfn_isNull(sDate)) 
	{
		return -1;
	}

	nMonth = parseInt(sDate.substr(4, 2), 10);
	if (nMonth == 1 || nMonth == 3 || nMonth == 5 || nMonth == 7 || nMonth == 8 || nMonth == 10 || nMonth == 12) 
	{
		nLastDate = 31;
	}
	else if (nMonth == 2) 
	{
		if (gfn_isLeapYear(sDate) == true) 
		{
			nLastDate = 29;
		}
		else 
		{
			nLastDate = 28;
		}
	}
	else 
	{
		nLastDate = 30;
	}

	return nLastDate;
}

/************************************************************************
* 함 수 명 : gfn_addDate
* 기    능 : 입력된 날자에 OffSet 으로 지정된 만큼의 일을 더한다.
* 인    자 : sDate  - 일자(yyyyMMdd) 
*             nOffSet  - 날짜로부터 증가 감소값. 지정하지 않으면 Default Value = 1 로 적용
* 리 턴 값 : string Date에 nOffset이 더해진 결과를 'yyyyMMdd'로 표현된 날짜
*************************************************************************/ 
gfn_addDate = function(sDate, nOffSet)
{
	if(gfn_isNull(sDate)) return;
	
	var nYear  = parseInt(gfn_subStr(sDate,0, 4));
	var nMonth = parseInt(gfn_subStr(sDate,4, 2));
	var nDate  = parseInt(gfn_subStr(sDate,6, 2)) + parseInt(nOffSet);

	return gfn_dateTime(nYear, nMonth, nDate);
}

/************************************************************************
* 함 수 명 : gfn_addMonth
* 기    능 : 입력된 날자에 OffSet 으로 지정된 만큼의 달을 더한다.
* 인    자 : sDate  - 일자(yyyyMMdd) 
*             nOffSet  - 날짜로부터 증가 감소값. 지정하지 않으면 Default Value = 1 로 적용
* 리 턴 값 : string Date에 nOffset이 더해진 결과를 'yyyyMMdd'로 표현된 날짜
*************************************************************************/ 
gfn_addMonth = function(sDate, nOffSet)
{
	if( gfn_isNull(sDate) || gfn_isNull(nOffSet) )	return "";
	
    var nYear 	= parseInt(sDate.substr(0, 4));
    var nMonth 	= parseInt(sDate.substr(4, 2)) + nOffSet;
    var nDate 	= parseInt(sDate.substr(6, 2));
	var nLastDate, sRet;
	
	if(nDate.toString().length == 1)
	{
		nDate = "0" + nDate;
	}
	
	sRet 		= gfn_dateTime(nYear, nMonth, 1);	
    nLastDate 	= gfn_getLastDateNum(sRet);
    sRet 		= sRet.substr(0, 6);
    
    if( nDate > nLastDate )
    {
		sRet += nLastDate.toString();
	} else {
		sRet += nDate.toString();
	}
		
	return sRet;
}

/************************************************************************
* 함 수 명 : gfn_getDiffDay
* 기    능 : 2개의 날짜간의 Day count
* 인    자 : sFdate - 시작일자
*             sTdate - 종료일자
* 리 턴 값 : Integer(차이 일수)
*************************************************************************/
gfn_getDiffDay = function(sFdate, sTdate)
{
	sFdate = new String(sFdate);
	sFdate = sFdate.split(" ").join("").split("-").join("").split("/").join("");
	sTdate = new String(sTdate);
	sTdate = sTdate.split(" ").join("").split("-").join("").split("/").join("");

	sFdate = gfn_left(sFdate, 8);
	sTdate = gfn_left(sTdate, 8);

	if (sFdate.length != 8 || sTdate.length != 8
		 //|| nexacro.isNumeric(sFdate) == false || nexacro.isNumeric(sTdate) == false
		 || gfn_getDay(sFdate) == -1 || gfn_getDay(sTdate) == -1) 
	{
		return null;
	}

	var nDiffDate = gfn_strToDate(sTdate) - gfn_strToDate(sFdate);
	var nDay = 1000 * 60 * 60 * 24;

	nDiffDate = parseInt(nDiffDate / nDay);
	if (nDiffDate < 0) 
	{
		nDiffDate = nDiffDate;	// - 1 : 해당일 포함
	}
	else 
	{
		nDiffDate = nDiffDate;	// + 1 : 해당일 포함
	}

	return nDiffDate;
}

/************************************************************************
* 함 수 명 : gfn_getDiffMonth
* 기    능 : 두 월간의 차이 월수 계산
* 인    자 : sFdate - 시작일자
*             sTdate - 종료일자
* 리 턴 값 : Integer 숫자 형태의 차이월수(sStartDate, sEndDate의 일은 포함하지 않고 계산)
*************************************************************************/
gfn_getDiffMonth = function(oStartDate, oEndDate)
{
	var nStartMon,nEndMon;
	var sStartDate,sEndDate;

	if (gfn_isNull(oStartDate) || gfn_isNull(oEndDate)) 
	{
		return NaN;
	}
	
	if ( gfn_isDateType(oStartDate) ) sStartDate = gfn_dateToStr(oStartDate);
	if ( gfn_isDateType(oEndDate) )   sEndDate = gfn_dateToStr(oEndDate);

	nStartMon = parseInt(sStartDate.substr(0, 4), 10) * 12 + parseInt(sStartDate.substr(4, 2), 10);
	nEndMon = parseInt(sEndDate.substr(0, 4), 10) * 12 + parseInt(sEndDate.substr(4, 2), 10);

	return (nEndMon - nStartMon);
}

/************************************************************************
* 함 수 명 : gfn_getDiffTime
* 기    능 : 두 시간간의 차이 계산
* 인    자 : sStartTime - HHmmss형태의 From 시간 ( 예 : "033025" )
*             sEndTime   - HHmmss형태의 To 시간 ( 예 : "034025" )
* 리 턴 값 : Integer(차이 시간)
*************************************************************************/
gfn_getDiffTime = function(sStartTime, sEndTime, sType) {
    var nReturnValue;
    var nFrom_HH = toNumber(sStartTime.substring(0, 2));
    var nFrom_mm = toNumber(sStartTime.substring(2, 4));
    var nFrom_ss = toNumber(sStartTime.substring(4, 6));
    
    var nTo_HH = toNumber(sEndTime.substring(0, 2));
    var nTo_mm = toNumber(sEndTime.substring(2, 4));
    var nTo_ss = toNumber(sEndTime.substring(4, 6));
    
    var nFromTotal_ss = (nFrom_HH * 3600) + (nFrom_mm * 60) + nFrom_ss;
    var nToTotal_ss   = (nTo_HH * 3600) + (nTo_mm * 60) + nTo_ss;
    
    if(sType == "HH") {
        return (Math.floor((nToTotal_ss - nFromTotal_ss) / 3600));
    } else if(sType == "mm") {
        return (Math.floor((nToTotal_ss - nFromTotal_ss) / 60));
    } else if(sType == "ss") {
        return (nToTotal_ss - nFromTotal_ss);
    }
}

/************************************************************************
* 함 수 명 : gfn_getDay
* 기    능 : 입력된 날자로부터 요일을 구함
* 인    자 : sDate  - 일자(yyyyMMdd)
* 리 턴 값 : Integer 요일에 따른 숫자
*************************************************************************/
gfn_getDay = function(sDate)
{
	var objDate = gfn_strToDate(sDate);
	return objDate.getDay();
}

/************************************************************************
* 함 수 명 : gfn_getDayName
* 기    능 : 입력된 날자로부터 요일명을 구함
* 인    자 : sDate  - 일자(yyyyMMdd)
*             sType  - 타입("E"-영어, "K"-한글)
* 리 턴 값 : string 요일명
*************************************************************************/
gfn_getDayName = function(sDate, sType)
{
	var objDayName;
	if ( gfn_isNull(sType) ) sType = "E";
	
	if ( sType == "E" ) {
		objDayName = new Array("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT");
	} else {
		objDayName = new Array("일", "월", "화", "수", "목", "금", "토");
	}
	var objDate = gfn_getDay(sDate);

	return objDayName[objDate];
}

/************************************************************************
* 함 수 명 : gfn_dateTime
* 기    능 : 년,월,일을 숫자로 받아서 YYYYMMDD형태로 변환( or YYYYMMDDHHMISS를 변환)
* 인    자 : nYear  - nYear (년도)
*             nMonth - nMonth (월)
*             nDate (일)
* 리 턴 값 : string 조합한 날짜를 리턴
*************************************************************************/
gfn_dateTime = function(nYear, nMonth, nDate)
{
	
	if (nYear.toString().trim().length >= 5) 
	{
		var sDate = new String(nYear);
		var nYear = sDate.substr(0, 4);
		var nMonth = sDate.substr(4, 2);
		var nDate = ((sDate.substr(6, 2) == "") ? 1 : sDate.substr(6, 2));
		var nHours = ((sDate.substr(8, 2) == "") ? 0 : sDate.substr(8, 2));
		var nMinutes = ((sDate.substr(10, 2) == "") ? 0 : sDate.substr(10, 2));
		var nSeconds = ((sDate.substr(12, 2) == "") ? 0 : sDate.substr(12, 2));

		var objDate = new Date(parseInt(nYear), parseInt(nMonth) - 1, parseInt(nDate), parseInt(nHours), parseInt(nMinutes), parseInt(nSeconds));
	}
	else 
	{
		var objDate = new Date(parseInt(nYear), parseInt(nMonth) - 1, parseInt(((nDate == null) ? 1 : nDate)));
	}

	var strYear = objDate.getFullYear().toString();
	var strMonth = (objDate.getMonth() + 1).toString();
	var strDate = objDate.getDate().toString();

	if (strYear.length == 2)
	{
		strYear = "19" + strYear;
	}
	if (strMonth.length == 1) 
	{
		strMonth = "0" + strMonth;
	}
	if (strDate.length == 1) 
	{
		strDate = "0" + strDate;
	}

	return strYear + strMonth + strDate;
}

/************************************************************************
* 함 수 명 : gfn_getMaskFormatString
* 기    능 : 주어진 날짜 객체의 Mask Format 처리된 문자열을 반환.
*            요일명칭, 월 명칭, 오전/오후 명칭 표시 처리는 Eco.date에 정의된 값으로 처리된다.
* 인    자 : {date} dt Date 개체.
*             {string} strMask mask할 format 문자열.
* 리 턴 값 : {string} 변환된 문자열.
* @example
* var dt = Eco.date.strToDate("20130430123412"); // convert Date type from "20130430123412".
* trace(getMaskFormatString(dt, "yyyy년 MM월 dd일 tt hh시 mm분 ss초")); // output : 2013년 04월 30일 오후 12시 34분 12초
* trace(getMaskFormatString(dt, "yyyy-MM-dd")); // output : 2013-04-30
* trace(getMaskFormatString(dt, "yy MM.dd")); // output : 13 04.30
* trace(getMaskFormatString(dt, "yyyy-MM-dd W \\Week")); // output : 2013-04-30 18 Week
* trace(getMaskFormatString(dt, "MMMM dddd")); // output : 4월 화요일
*************************************************************************/
gfn_getMaskFormatString = function(dt, strMask)
{
	if(gfn_isNull(dt)) return "";
	dt = gfn_strToDate(dt);
	var arrMask = _parseDateMask(strMask),
		arrDt = [], mask, h;
	for ( var i = 0, len = arrMask.length; i < len ; i++ )
	{
		mask = arrMask[i];
		if ( mask > -1 )
		{
			arrDt[arrDt.length] = strMask.charAt(mask);
		}
		else
		{
			switch (mask)
			{
				case "yyyy": arrDt[arrDt.length] = new String(dt.getFullYear()); break;
				case "MMMM": arrDt[arrDt.length] = monthName[dt.getMonth()]; break;
				case "dddd": arrDt[arrDt.length] = weekName[dt.getDay()]; break;
				case "MMM": arrDt[arrDt.length] = monthShortName[dt.getMonth()]; break;
				case "ddd": arrDt[arrDt.length] = weekShortName[dt.getDay()]; break;
				case "sss": arrDt[arrDt.length] = gfn_lPad(new String(dt.getMilliseconds()),'0',3); break;
				case "yy": arrDt[arrDt.length] = gfn_lPad(new String(dt.getFullYear() % 1000),'0',2); break;
				case "MM": arrDt[arrDt.length] = gfn_lPad(new String(dt.getMonth() + 1),'0',2); break;
				case "WW": arrDt[arrDt.length] = gfn_lPad(new String(getWeekNumber(dt)),'0',2); break;
				case "dd": arrDt[arrDt.length] = gfn_lPad(new String(dt.getDate()),'0',2); break;
				case "HH": arrDt[arrDt.length] = gfn_lPad(new String(dt.getHours()),'0',2); break;
				case "hh": arrDt[arrDt.length] = gfn_lPad(new String(((h = dt.getHours() % 12) ? h : 12)),'0',2); break;
				case "mm": arrDt[arrDt.length] = gfn_lPad(new String(dt.getMinutes()),'0',2); break;
				case "ss": arrDt[arrDt.length] = gfn_lPad(new String(dt.getSeconds()),'0',2); break;
				case "tt": arrDt[arrDt.length] = dt.getHours() < 12 ? ttName[0] : ttName[1]; break;
				case "M": arrDt[arrDt.length] = new String(dt.getMonth() + 1); break;
				case "d": arrDt[arrDt.length] = new String(dt.getDate()); break;
				case "H": arrDt[arrDt.length] = new String(dt.getHours()); break;
				case "h": arrDt[arrDt.length] = new String(((h = dt.getHours() % 12) ? h : 12)); break;
				case "m": arrDt[arrDt.length] = new String(dt.getMinutes()); break;
				case "s": arrDt[arrDt.length] = new String(dt.getSeconds()); break;
				case "W": arrDt[arrDt.length] = new String(gfn_getWeekOfYear(dt)); break;
			}
		}
	}
	return arrDt.join("");
}

/************************************************************************
* 함 수 명 : _parseDateMask
* 기    능 : format 변환 정보 반환.
* 인    자 : {string} strMask mask할 format 문자열.
* 리 턴 값 : {array} mask format 변환 정보.
*************************************************************************/
_parseDateMask = function(strMask)
{
	var res = _dateMaskCache[strMask];
	if ( res ) return res;

	var arrMask = [], tokenStr, seq = 0,
		bEscape = false, bQuote = false,
		maskArr = strMask.split(""),
		tmpStr;

	for ( var i = 0, len = maskArr.length ; i < len ; )
	{
		tokenStr = maskArr[i];
		//trace(i + "===>" + tokenStr);
		if( bEscape == false && tokenStr == "'" ) 
		{ // Mask가 Quotation이 시작될 경우.
			if( bQuote == false )
				bQuote = true;
			else
				bQuote = false;
			i++;
			continue;
		}
		if( bEscape == false && tokenStr == "\\" && !bQuote ) 
		{ // Mask에서 Escape에 진입할 경우.
			bEscape = true;
			i++;
			continue;
		}
		else if ( bEscape ) 
		{ // Mask에서 Escape를 사용할 경우.
			//trace(i + "(EEE)===>" + tokenStr);
			arrMask[seq] = i;
			seq++;
			bEscape = false;
		}
		else if( bQuote == false ) 
		{// Mask에서 Quotation 밖의 글자에 대해.
			tmpStr = strMask.substr(i, 4);
			if ( tmpStr == "yyyy" || tmpStr == "MMMM" || tmpStr == "dddd" ) //yyyy, MMMM, dddd
			{
				arrMask[seq] = tmpStr;
				i += 4;
				seq++;
				continue;
			}
			tmpStr = strMask.substr(i, 3);
			if ( tmpStr == "MMM" || tmpStr == "ddd" || tmpStr == "sss" ) //MMM, ddd, sss
			{
				arrMask[seq] = tmpStr;
				i += 3;
				seq++;
				continue;
			}
			tmpStr = strMask.substr(i, 2);
			if ( tmpStr == "yy" || tmpStr == "MM" || tmpStr == "dd" ||
				 tmpStr == "HH" || tmpStr == "mm" || tmpStr == "ss" ||
				 tmpStr == "hh" || tmpStr == "tt" || tmpStr == "tt" ) // yy, MM, dd, HH, mm , ss, tt, WW
			{
				arrMask[seq] = tmpStr;
				i += 2;
				seq++;
				continue;
			}

			if ( tokenStr == "M" || tokenStr == "d" || tokenStr == "H" ||
				 tokenStr == "h" || tokenStr == "m" || tokenStr == "s" || tokenStr == "W" ) // M, d, H, h, m, s, W
			{
				arrMask[seq] = tokenStr;
				seq++;
			}
			else
			{
				arrMask[seq] = i;
				seq++;
			}
		}
		i++;
	}
	
	_dateMaskCache[strMask] = arrMask;
	
	return arrMask;
}

/************************************************************************
* 함 수 명 : gfn_getWeekOfYear
* 기    능 : yyyyMMdd 형태의 날짜를 입력하면 해당연도의 날짜에 해당하는 주차반환.
* 인    자 : {date|string} date Date Object 또는 날짜형 문자열.
* 리 턴 값 : {number} 주차에 해당하는 숫자.
*************************************************************************/
gfn_getWeekOfYear = function(date)
{
	if ( gfn_isString(date) )
	{
		date = strToDate(date);
	}
	if ( !gfn_isDate(date) )
	{
		return -1;
	}
	
	var onejan = new Date();
	
	onejan.setYear(date.getFullYear());
	onejan.setMonth(0);
	onejan.setDate(1);	
							
	return Math.ceil((((date - onejan) / 86400000) + onejan.getDay()+1)/7);
}

 
/************************************************************************
* 함 수 명 : gfn_getFirstDate
* 기    능 : 전달된 月의 1일 만들기.
* 인    자 : {string | date} value Date object 또는 yyyyMMdd 형태의 일자.
* 리 턴 값 : {string} yyyyMM01.
*************************************************************************/
gfn_getFirstDate = function(objValue)
{
	if ( gfn_isDateType(objValue) )
	{
		var dt = new Date();
		dt.setFullYear(objValue.getFullYear(), objValue.getMonth(), 1);
		dt.setHours(objValue.getHours(), objValue.getMinutes(), objValue.getSeconds());
		dt.setMilliseconds(objValue.getMilliseconds());
		return dt;
	}
	else
	{
		var sRtn = "";
		var dDate = new Date(parseInt(objValue.substr(0,4)), parseInt(objValue.substr(4,2))-1, 1);
		sRtn =  dDate.getFullYear()
			 + (dDate.getMonth()+1).toString().padLeft(2, "0")
			 +  dDate.getDate().toString().padLeft(2, "0");

		return sRtn;
	}
}

/************************************************************************
* 함 수 명 : gfn_getThisMonthBegin
* 기    능 : 금월의 시작일자를 문자열 형태로 리턴한다.(PC일자)
* 인    자 : 
* 리 턴 값 : {string} yyyyMM01. 금월시작일자
*************************************************************************/
gfn_getThisMonthBegin = function()
{
	var strFrom = gfn_today();
	return (strFrom.substr(0, 6) + "01");
}

/************************************************************************
* 함 수 명 : gfn_getThisWeekBegin
* 기    능 : 금주의 시작일자를 문자열 형태로 리턴한다.
* 인    자 : 
* 리 턴 값 : {string} 금주시작일자
*************************************************************************/
gfn_getThisWeekBegin = function() 
{
	var today = new Date();
	var fromDate = new Date (today.getYear(), today.getMonth(), today.getDate() - today.getDay());
	return gfn_dateToStr(fromDate);
}

/************************************************************************
* 함 수 명 : gfn_getYear
* 기    능 : 해당년도 구하기
* 인    자 : sDate  - 일자(yyyyMMdd) 
* 리 턴 값 : string yyyy형태의 년도 ( 예 : "2012" )
*************************************************************************/
gfn_getYear = function(sDate)
{
	if (gfn_isNull(sDate)) 
	{
		sDate = gfn_today();
	}

	return sDate.substr(0, 4);
}

/************************************************************************
* 함 수 명 : gfn_getYearMonth
* 기    능 : 해당년월 구하기
* 인    자 : sDate  - 일자(yyyyMMdd) 
* 리 턴 값 : string yyyyMM형태의 년월 ( 예 : "201211" )
*************************************************************************/
gfn_getYearMonth = function(sDate)
{
	if (gfn_isNull(sDate)) 
	{
		sDate = gfn_today();
	}

	return sDate.substr(0, 6);
}

/************************************************************************
* 함 수 명 : gfn_getMonth
* 기    능 : 해당월 구하기
* 인    자 : sDate  - 일자(yyyyMMdd) 
* 리 턴 값 : string MM형태의 년월 ( 예 : "11" )
*************************************************************************/
gfn_getMonth = function(sDate)
{
	if (gfn_isNull(sDate)) 
	{
		sDate = gfn_today();
	}

	return sDate.substr(4, 2);
}

/************************************************************************
* 함 수 명 : gfn_getStartOfWeek
* 기    능 : 해당일자의  주 시작일자를 계산
* 인    자 : ObjCal  - 일자 date
* 리 턴 값 : String 주 시작일자
*************************************************************************/
gfn_getStartOfWeek = function(ObjCal) 
{	
	var Day  = ObjCal.getDayOfWeek();
	var minusDay = 0 - Day;
	var objDate = new Date(ObjCal.getYear(), ObjCal.getMonth()-1, ObjCal.getDay());
	if(Day == 0) return gfn_dateToStr(objDate);
	var nMs = objDate.addDate(minusDay);
	return gfn_dateToStr(objDate);
}

/************************************************************************
* 함 수 명 : gfn_getEndOfWeek
* 기    능 : 해당일자의  주 마지막일자를 계산
* 인    자 : ObjCal  - 일자 date
* 리 턴 값 : String 주 종료일자
*************************************************************************/
gfn_getEndOfWeek = function(ObjCal) 
{
	var nLastDay = 6;
	var Day      = ObjCal.getDayOfWeek();
	
	var objDate = new Date(ObjCal.getYear(), ObjCal.getMonth()-1, ObjCal.getDay());
	if(Day == nLastDay) return gfn_dateToStr(objDate);
	
	var lastDate = new Date(ObjCal.getYear(), ObjCal.getMonth()-1, ObjCal.getDay());
	
	var objDay  = objDate.getDate();
	
	lastDate.addDate(nLastDay - Day);
	var lastDay = lastDate.getDate();
	
	return gfn_dateToStr(lastDate);
}

/************************************************************************
* 함 수 명 : gfn_secToTime
* 기    능 : 초형식을 시간형식으로 변환
* 인    자 : sValue  - 초단위의 시간 (SSSSS)
* 리 턴 값 : Strimg 내부시간형식 (HHMMSS)
*************************************************************************/
gfn_secToTime= function(sValue)
{
	var sReturnValue = "";
	var iHH = "";
	var iMM = "";
	var iSS = "";
	var sHH = "";
	var sMM = "";
	var sSS = "";

	if( gfn_isNull(sValue) || sValue < 0 )
	{
		sReturnValue = 0;
	}
	else {
		iHH = parseInt(nexacro.toNumber(sValue)/3600);
		iMM = parseInt((nexacro.toNumber(sValue) - iHH*3600)/60);
		iSS = parseInt((nexacro.toNumber(sValue) - iHH*3600)%60);

		if(iHH<10)
		{
			sHH = "0" + iHH;
		}
		else sHH = iHH;

		if(iMM<10)
		{
			sMM = "0" + iMM;
		}
		else sMM = iMM;

		if(iSS<10)
		{
			sSS = "0" + iSS;
		}
		else sSS = iSS;

		sReturnValue = sHH.toString() + sMM.toString() + sSS.toString();
	}
	return gfn_trim(sReturnValue);
}

/************************************************************************
* 함 수 명 : gfn_isLeapYear
* 기    능 : 윤년여부 확인
* 인    자 : sDate  - 일자(yyyyMMdd)
* 리 턴 값 : boolean
*************************************************************************/
gfn_isLeapYear = function(sDate)
{
	var ret;
	var nY;

	if (gfn_isNull(sDate)) 
	{
		return false;
	}

	nY = parseInt(sDate.substring(0, 4), 10);

	if ((nY % 4) == 0) 
	{
		if ((nY % 100) != 0 || (nY % 400) == 0) 
		{
			ret = true;
		}
		else 
		{
			ret = false;
		}
	}
	else 
	{
		ret = false;
	}

	return ret;
}

function formatDate(str, mark) {
	/*
	 * ********************************************************************************************************
	 * 함수설명 : 날짜형식으로 년,월,일 사이에 구분자를 넣어준다. str : 날짜가 YYMMDD형식으로 담겨있는 문자열 mark :
	 * 년,월,일 사이에 들어갈 구분자
	 * **********************************************************************************************************
	 */
	if (str != "" && str.length == 8) {
		return str.substring(0, 4) + mark + str.substring(4, 6) + mark
				+ str.substring(6, 8);
	} else {
		return "";
	}
}

function setToday(field) {
	/*
	 * ***************************************************************************************************
	 * 함수설명: 입력란을 오늘날짜로 채워준다.
	 * 
	 * field : html에서 name으로 지정된 입력필드의 명
	 * 
	 * 사용예
	 * ***************************************************************************************************
	 */
	var cDate = new Date();
	var year = cDate.getYear();
	var month = (cDate.getMonth() + 1).toString();
	month = month.length == 1 ? "0" + month : month;
	var day = cDate.getDate().toString();
	day = day.length == 1 ? "0" + day : day;
	field.value = "" + year + month + day;
}

function setMonthFirstDay(field) {
	/*
	 * ***************************************************************************************************
	 * 함수설명: 입력란을 이번달의 첫날로 채워준다..
	 * 
	 * field : html에서 name으로 지정된 입력필드의 명
	 * 
	 * 사용예
	 * ***************************************************************************************************
	 */
	var cDate = new Date();
	var year = cDate.getYear();
	var month = (cDate.getMonth() + 1).toString();
	month = month.length == 1 ? "0" + month : month;
	var day = "01";
	field.value = "" + year + month + day;
}

function setYearFirstDay(field) {
	/*
	 * ***************************************************************************************************
	 * 함수설명: 입력란을 올해 1월1일로 채워준다.
	 * 
	 * field : html에서 name으로 지정된 입력필드의 명
	 * 
	 * 사용예
	 * ***************************************************************************************************
	 */

	var cDate = new Date();
	var year = cDate.getYear();
	var month = "01";
	var day = "01";
	field.value = "" + year + month + day;
}

function setOneMonthBefore(field) {
	/*
	 * ***************************************************************************************************
	 * 함수설명: 입력란을 오늘보다 한달전의 날짜로 채워준다..
	 * 
	 * field : html에서 name으로 지정된 입력필드의 명
	 * 
	 * 사용예
	 * ***************************************************************************************************
	 */
	var cDate = new Date();
	var year = cDate.getYear();
	var month = (cDate.getMonth()).toString();
	month = month.length == 1 ? "0" + month : month;
	if (month == "00") {
		month = "12";
		year--;
	}
	var day = cDate.getDate().toString();
	day = day.length == 1 ? "0" + day : day;
	field.value = "" + year + month + day;
}

function setOneYearBefore(field) {
	/*
	 * ***************************************************************************************************
	 * 함수설명: 입력란을 오늘보다 1년전의 날짜로 채워준다..
	 * 
	 * field : html에서 name으로 지정된 입력필드의 명
	 * 
	 * 사용예
	 * ***************************************************************************************************
	 */
	var cDate = new Date();
	var year = cDate.getYear() - 1;
	var month = (cDate.getMonth() + 1).toString();
	month = month.length == 1 ? "0" + month : month;
	var day = cDate.getDate().toString();
	day = day.length == 1 ? "0" + day : day;
	field.value = "" + year + month + day;
}

function gfn_formatDate(val) {
	var returnTxt = "";
	
	if( !gfn_isNull(val) ){
		if( val.length == 8 ){
			returnTxt = val.substring(0,4) +"년 "+ val.substring(4,6) +"월 "+ val.substring(6,8) +"일";
		}
	}
	
	return returnTxt;
}

function gfn_formatDateHyphen(val, division) {
	var returnTxt = "";
	
	if( gfn_isNull(division) ) division = "-";
	
	if( !gfn_isNull(val) ){
		if( val.length == 8 ){
			returnTxt = val.substring(0,4) +division+ val.substring(4,6) +division+ val.substring(6,8);
		}
	}
	
	return returnTxt;
}

function gfn_makeFromYear(cboId, val){
	var sYear = parseInt(new Date().toString('yyyy'));
	
	$(cboId).empty();
	
	for(i=(sYear-6);i<(sYear+3);i++){
		$(cboId).append($('<option>', { 
	        value: i,
	        text : i
		}));
	}
	
	$(cboId).val( (gfn_isNull(val) ? sYear : val) );
}

function gfn_makeFromMonth(cboId, val){
	var sMonth = parseInt(new Date().toString('MM'));
	
	$(cboId).empty();
	
	for(i=1;i<=12;i++){
		$(cboId).append($('<option>', { 
			value: ((i < 10) ? "0"+i : i),
	        text : ((i < 10) ? "0"+i : i)
		}));
	}
	
	$(cboId).val( (gfn_isNull(val) ? sMonth : val) );
}

function gfn_makeFromDay(cboId, val){
	var sDay = parseInt(new Date().toString('dd'));
	var sLastDay = Date.today().moveToLastDayOfMonth().toString('dd');
	
	$(cboId).empty();
	
	for(i=1;i<=sLastDay;i++){
		$(cboId).append($('<option>', { 
			value: ((i < 10) ? "0"+i : i),
	        text : ((i < 10) ? "0"+i : i)
		}));
	}
	
	$(cboId).val( (gfn_isNull(val) ? sDay : val) );
}

function gfn_makeToYear(cboId){
	var sYear = parseInt(Date.today().addMonths(2).toString('yyyy'));
	
	$(cboId).empty();
	
	for(i=(sYear-6);i<(sYear+3);i++){
		$(cboId).append($('<option>', { 
	        value: i,
	        text : i
		}));
	}
	
	$(cboId).val( sYear );
}

function gfn_makeToMonth(cboId){
	var sMonth = parseInt(Date.today().addMonths(2).toString('MM'));
	
	$(cboId).empty();
	
	for(i=1;i<=12;i++){
		$(cboId).append($('<option>', { 
			value: ((i < 10) ? "0"+i : i),
	        text : ((i < 10) ? "0"+i : i)
		}));
	}
	
	$(cboId).val( sMonth );
}

function gfn_makeToDay(cboId){
	var sDay = parseInt(Date.today().addMonths(2).toString('dd'));
	var sLastDay = Date.today().addMonths(2).moveToLastDayOfMonth().toString('dd');
	
	$(cboId).empty();
	
	for(i=1;i<=sLastDay;i++){
		$(cboId).append($('<option>', { 
			value: ((i < 10) ? "0"+i : i),
	        text : ((i < 10) ? "0"+i : i)
		}));
	}
	
	$(cboId).val( sDay );
}























