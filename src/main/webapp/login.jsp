<%-- 
    Document   : login
    Created on : Jan 24, 2012, 6:59:00 PM
    Author     : jdmr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s"    uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="login.title" /></title>
        <style type='text/css' media='screen'>
            #login {
                margin: 15px 0px;
                padding: 0px;
                text-align: center;
            }

            #login .inner {
                width: 340px;
                padding-bottom: 6px;
                margin: 60px auto;
                text-align: left;
                border: 1px solid #aab;
                background-color: #f0f0fa;
                -moz-box-shadow: 2px 2px 2px #eee;
                -webkit-box-shadow: 2px 2px 2px #eee;
                -khtml-box-shadow: 2px 2px 2px #eee;
                box-shadow: 2px 2px 2px #eee;
            }

            #login .inner .fheader {
                padding: 18px 26px 14px 26px;
                background-color: #f7f7ff;
                margin: 0px 0 14px 0;
                color: #2e3741;
                font-size: 18px;
                font-weight: bold;
            }

            #login .inner .cssform p {
                clear: left;
                margin: 0;
                padding: 4px 0 3px 0;
                padding-left: 105px;
                margin-bottom: 20px;
                height: 1%;
            }

            #login .inner .cssform input[type='text'] {
                width: 120px;
            }

            #login .inner .cssform label {
                font-weight: bold;
                float: left;
                text-align: right;
                margin-left: -105px;
                width: 110px;
                padding-top: 3px;
                padding-right: 10px;
            }

            #login #remember_me_holder {
                padding-left: 120px;
            }

            #login #submit {
                margin-left: 15px;
            }

            #login #remember_me_holder label {
                float: none;
                margin-left: 0;
                text-align: left;
                width: 200px
            }

            #login .inner .login_message {
                padding: 6px 25px 20px 25px;
                color: #c33;
            }

            #login .inner .text_ {
                width: 120px;
            }

            #login .inner .chk {
                height: 12px;
            }
        </style>

    </head>
    <body>
        <div id='login'>
            <div class='inner'>
                <div class='fheader'><s:message code="login.title" /></div>



                <form action='<c:url value="/entrar" />' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                    <p>
                        <label for='username'><s:message code="login.username" /></label>
                        <input type='text' class='text_' name='j_username' id='username'/>
                    </p>

                    <p>
                        <label for='password'><s:message code="login.password" /></label>
                        <input type='password' class='text_' name='j_password' id='password'/>
                    </p>

                    <p id="remember_me_holder">
                        <input type='checkbox' class='chk' name='_spring_security_remember_me' id='remember_me' />
                        <label for='remember_me'><s:message code="login.remember.me" /></label>
                    </p>

                    <p>
                        <input type='submit' id="submit" value='<s:message code="login.entrar" />'/>
                    </p>
                </form>
            </div>
        </div>
        <script type='text/javascript'>
            <!--
            (function() {
                document.forms['loginForm'].elements['j_username'].focus();
            })();
            // -->
        </script>
    </body>
</html>