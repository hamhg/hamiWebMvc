<%--
  Program Name : dbTest.jsp
  Description  :
  History      : 2017. 7. 22.
  Author       : HHG
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<sql:query var="rs" dataSource="jdbc/hamiDB">
    select login_id, password from TSYAU0001 where rownum < 20
</sql:query>
<html>
<head>
    <meta charset="UTF-8">
    <!--IE Compatibility modes-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--Mobile first-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Metis</title>

    <meta name="description" content="Free Admin Template Based On Twitter Bootstrap 3.x">
    <meta name="author" content="">
</head>
<body>
<program id="${programId}">
    <div class="bg-dark dk" id="db_wrap">
        <c:import url="ajax.jsp"/>
    </div>
    <div id="content" class="starter-template">
        <div class="inner bg-light lter">
            <div class="col-lg-12">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    This content will only be visible to users who have
                    the "supervisor" authority in their list of <tt>GrantedAuthority</tt>s.
                </sec:authorize>
                <h2>DB Test</h2>
                <table class="table table-bordered">
                    <tr>
                        <th>Username</th>
                        <th>Password</th>
                    </tr>
                    <c:forEach var="row" items="${rs.rows}">
                        <tr>
                            <td> ${row.login_id} </td>
                            <td> ${row.password} </td>
                        </tr>
                    </c:forEach>
                </table><br>
            </div>
        </div>
    </div>
</program>
</body>
</html>