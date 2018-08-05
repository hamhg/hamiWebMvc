<%--
  Program Name : login.jsp
  Description  :
  History      : 2017. 7. 28.
  Author       : HHG
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<!DOCTYPE html>
<html lang="utf-8">
<head>
    <title>Login Page</title>
    <meta charset="UTF-8">
    <!--IE Compatibility modes-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--Mobile first-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Login Page</title>

    <meta name="description" content="Free Admin Template Based On Twitter Bootstrap 3.x">
    <meta name="author" content="">

    <meta name="msapplication-TileColor" content="#5bc0de" />
    <meta name="msapplication-TileImage" content="<c:url value='assets/img/metis-tile.png' />" />

    <!-- Bootstrap -->
    <link rel="stylesheet" href="<c:url value='/assets/lib/bootstrap/css/bootstrap.css' />">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="<c:url value='/assets/lib/font-awesome/css/font-awesome.css' />">

    <!-- Metis sys stylesheet -->
    <link rel="stylesheet" href="<c:url value='/assets/css/main.css' />">

    <!-- metisMenu stylesheet -->
    <link rel="stylesheet" href="<c:url value='/assets/lib/metismenu/metisMenu.css' />">

    <!-- onoffcanvas stylesheet -->
    <link rel="stylesheet" href="<c:url value='/assets/lib/onoffcanvas/onoffcanvas.css' />">

    <!-- animate.css stylesheet -->
    <link rel="stylesheet" href="<c:url value='/assets/lib/animate.css/animate.css' />">


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body class="login" onload='document.loginForm.username.focus();'>

<div class="form-signin" id="login-box">
    <div class="text-center">
        <img src="<c:url value='/assets/img/logo.png' />" alt="Metis Logo">
    </div>
    <hr>
    <div class="tab-content">
        <div id="login" class="tab-pane active">
            <form name='loginForm' action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />" method='POST'>
                <c:if test="${not empty error}">
                    <p class="text-muted text-center">${error}</p>
                </c:if>
                <c:if test="${not empty msg}">
                    <p class="text-muted text-center">${msg}</p>
                </c:if>
                <c:if test="${empty error}">
                    <p class="text-muted text-center">
                        Enter your username and password
                    </p>
                </c:if>
                <input type="text" placeholder="Username" name="username" class="form-control top">
                <input type="password" placeholder="Password" name="password" class="form-control bottom">
                <c:if test="${empty loginUpdate}">
                <!-- if this is login for update, ignore remember me check -->
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="remember-me"> Remember Me
                        </label>
                    </div>
                </c:if>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </div>
        <div id="forgot" class="tab-pane">
            <form action="<c:url value='index.html' />">
                <p class="text-muted text-center">Enter your valid e-mail</p>
                <input type="email" placeholder="mail@domain.com" class="form-control">
                <br>
                <button class="btn btn-lg btn-danger btn-block" type="submit">Recover Password</button>
            </form>
        </div>
        <div id="signup" class="tab-pane">
            <form action="<c:url value='index.html' />">
                <input type="text" placeholder="username" class="form-control top">
                <input type="email" placeholder="mail@domain.com" class="form-control middle">
                <input type="password" placeholder="password" class="form-control middle">
                <input type="password" placeholder="re-password" class="form-control bottom">
                <button class="btn btn-lg btn-success btn-block" type="submit">Register</button>
            </form>
        </div>
    </div>
    <hr>
    <div class="text-center">
        <ul class="list-inline">
            <li><a class="text-muted" href="#login" data-toggle="tab">Login</a></li>
            <li><a class="text-muted" href="#forgot" data-toggle="tab">Forgot Password</a></li>
            <li><a class="text-muted" href="#signup" data-toggle="tab">Signup</a></li>
        </ul>
    </div>
</div>

<!--jQuery -->
<script src="<c:url value='assets/lib/jquery/jquery.js' />"></script>

<!--Bootstrap -->
<script src="<c:url value='assets/lib/bootstrap/js/bootstrap.js' />"></script>

<script type="text/javascript">
    (function($) {
        $(document).ready(function() {
            $('.list-inline li > a').click(function() {
                var activeForm = $(this).attr('href') + ' > form';
                //console.log(activeForm);
                $(activeForm).addClass('animated fadeIn');
                //set timer to 1 seconds, after that, unload the animate animation
                setTimeout(function() {
                    $(activeForm).removeClass('animated fadeIn');
                }, 1000);
            });
        });
    })(jQuery);
</script>
</body>

</html>
