<!DOCTYPE html>
<html lang="en">
<head>

    <!-- Basic Page Needs
  ================================================== -->
    <meta charset="utf-8">
    <title>Login</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Mobile Specific Metas
  ================================================== -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <!-- CSS
  ================================================== -->

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <%--<link href="js/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <script src="js/jquery.1.5.min.js"></script>--%>
    <script src="js/jquery-1.10.2.min.js"></script>

    <script language="JavaScript">
        $(document).ready(function(){
            // constants
            var SHOW_CLASS = 'show',
                    HIDE_CLASS = 'hide',
                    ACTIVE_CLASS = 'active';

            $( '.tabs' ).on( 'click', 'li a', function(e){
                e.preventDefault();
                var $tab = $( this ),
                        href = $tab.attr( 'href' );

                $( '.active' ).removeClass( ACTIVE_CLASS );
                $tab.addClass( ACTIVE_CLASS );

                $( '.show' )
                        .removeClass( SHOW_CLASS )
                        .addClass( HIDE_CLASS )
                        .hide();

                $(href)
                        .removeClass( HIDE_CLASS )
                        .addClass( SHOW_CLASS )
                        .hide()
                        .fadeIn( 550 );
            });
        });
    </script>
</head>
<body>

<jsp:include page="errorheader.jsp"/>

<div class="container">
    <div class="flat-form">
        <ul class="tabs">
            <li>
                <a href="#login" class="active">Login</a>
            </li>
            <li>
                <a href="#register">Register</a>
            </li>
            <li>
                <a href="#"></a>
            </li>
        </ul>
        <div id="login" class="form-action show">
            <h1>Login here</h1>
            <p>
                Login to upload and/or download files to/from the Server.
            </p>
            <form action="controller" method="post">
                <ul>
                    <li>
                        <input type="text" placeholder="Username" id="username" name="username"/>
                    </li>
                    <li>
                        <input type="password" placeholder="Password" name="password"/>
                    </li>
                    <li>
                        <input type="hidden" name="_c" value="login"/>
                        <input type="submit" value="Login" class="button" />
                    </li>
                </ul>
            </form>
        </div>
        <!--/#login.form-action-->
        <div id="register" class="form-action hide">
            <h1>Register</h1>
            <p>
                Please register yourself to upload/download files.
            </p>
            <form action="controller" method="POST">
                <ul>
                    <li>
                        <input type="text" placeholder="Username" name="username"/>
                    </li>
                    <li>
                        <input type="password" placeholder="Password" name="password"/>
                    </li>
                    <li>
                        <input type="text" placeholder="Name" name="name"/>
                    </li>
                    <li>
                        <input type="hidden" name="_c" value="register"/>
                        <input type="submit" value="Sign Up" class="button" />
                    </li>
                </ul>
            </form>
        </div>

    </div>
</div>
<script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script>
    document.getElementById("username").focus();
</script>
</body>
</html>


