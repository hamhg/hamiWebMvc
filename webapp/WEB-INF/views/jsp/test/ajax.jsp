<%--
  Program Name : welcome.jsp
  Description  :
  History      : 2017. 7. 29.
  Author       : HHG
--%>
<%@page session="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="utf-8">
<head>
    <title>Spring MVC 4 + Ajax Hello World</title>
</head>
<program id="${programId}">
<div class="tab-pane" id="aj_wrap">
    <div class="row">
        <ol class="breadcrumb page-breadcrumb">
            <li><a href="index.html">Home</a></li>
            <li class="active">AjaxTest (JSP)</li>
        </ol>
    </div>
    <div id="content" class="page-header">
        <div class="row">
            <div class="col-md-4 text-xs-center text-md-left text-nowrap">
                <h1><i class="page-header-icon ion-ios-paper-outline"></i>AjaxTest (JSP)</h1>
            </div>

            <hr class="page-wide-block visible-xs visible-sm">

            <div class="col-xs-12 width-md-auto width-lg-auto width-xl-auto pull-md-right">
                <a href="#" class="btn btn-primary btn-block"><span class="btn-label-icon left ion-plus-round"></span>Create project</a>
            </div>

            <!-- Spacer -->
            <div class="m-b-2 visible-xs visible-sm clearfix"></div>

            <form action="" class="page-header-form col-xs-12 col-md-4 pull-md-right">
                <div class="input-group">
                    <span class="input-group-addon b-a-0 font-size-16"><i class="ion-search"></i></span>
                    <input type="text" placeholder="Search..." class="form-control p-l-0 b-a-0">
                </div>
            </form>
        </div>
    </div>
    <h3>Search Form</h3>

    <div id="feedback"></div>

    <form class="form-horizontal" id="search-form">
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Username</label>
            <div class="col-sm-10">
                <input type=text class="form-control" id="username">
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="email">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" id="bth-search"
                        class="btn btn-primary btn-lg">Search
                </button>
            </div>
        </div>
    </form>
    <hr class="page-block m-t-0 m-b-0">
</div>

<script>
    jQuery(document).ready(function ($) {

        $("#${programId} #search-form").submit(function (event) {

            // Disble the search button
            enableSearchButton(false);

            // Prevent the form from submitting via the browser.
            event.preventDefault();

            searchViaAjax();

        });

        function searchViaAjax() {

            var search = {
                "username": $("#${programId} #username").val(),
                "email": $("#${programId} #email").val()
            };

            var token = $("#csrf").val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "${home}search/api/getSearchResult",
                data: JSON.stringify(search),
                dataType: 'json',
                timeout: 100000,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (data) {
                    console.log("SUCCESS: ", data);
                    display(data);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                    display(e);
                },
                done: function (e) {
                    console.log("DONE");
                    enableSearchButton(true);
                }
            });
        }

        function enableSearchButton(flag) {
            $("#${programId} #btn-search").prop("disabled", flag);
        }

        function display(data) {
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#${programId} #feedback').html(json);
        }
    });

</script>
</program>
</body>
</html>