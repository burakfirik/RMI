<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.pk.common.AppData" %>
<%
    List<String[]> rows = (List) request.getAttribute("rows");
    if (rows == null) {
        rows = new ArrayList();
    }

    String name = AppData.userInfo.getName();
    if (name == null) name = "";
%>
<!DOCTYPE html>
<html lang="en">
<title>Table List</title>
<head>
    <link rel="stylesheet" href="css/main.css">

    <link href="media/dataTables/demo_page.css" rel="stylesheet" type="text/css"/>
    <link href="media/dataTables/demo_table.css" rel="stylesheet" type="text/css"/>
    <link href="media/dataTables/demo_table_jui.css" rel="stylesheet" type="text/css"/>
    <link href="media/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="media/themes/smoothness/jquery-ui-1.7.2.custom.css" rel="stylesheet" type="text/css" media="all"/>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script type="text/javascript">


        $(document).ready(function () {
            $("#tableData").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true
            });
        });
    </script>

    <script language="JavaScript">
        function logout() {
            document.getElementById("_c").value="l0out";
            document.dummyForm.submit();
        }
        function refresh() {
            document.getElementById("_c").value="open_table";
            document.dummyForm.submit();
        }

        function download(filename) {
            document.getElementById("_c").value="download";
            document.getElementById("file_name").value=filename;
            document.dummyForm.submit();
        }
        function deleteFile(filename) {
            document.getElementById("_c").value="delete";
            document.getElementById("file_name").value=filename;
            document.dummyForm.submit();
        }
    </script>
</head>

<body style="background: #819FF7;color:#1a1a1a">
<div id="menubar">
    <table width="100%">

        <tr>
            <td width="33%" align="left" style="vertical-align: middle;">Client App</td>
            <td align="center" style="vertical-align: middle;">Welcome: <%=name%>
            </td>

            <td align="right" width="33%" style="vertical-align: middle;">
                    <a href="javascript:refresh();" title="Refresh"><img src="img/refresh.png" alt="Refresh"></a>
                    <a href="javascript:logout();" title="Logout"><img src="img/logout.png" alt="Logout"></a>
            </td>
            <form name="dummyForm" action="controller" method="POST">
                <input type="hidden" name="_c" id="_c" value="l0out">
                <input type="hidden" name="file_name" id="file_name" value="">
            </form>

        </tr>
    </table>
</div>
<jsp:include page="errorheader.jsp"/>
<div id="upload">

    <form action="controller" method="post" enctype="multipart/form-data">
        Select file: <input type="file" id="file" name="file"/>
        <input type="hidden" name="_c" value="upload"/>
        <input type="submit" value="Upload" class="button2" />
    </form>

</div>
<hr>

<div id="container">
    <h1>FILE LIST</h1>
    <HR>
    <div id="data">
        <table id="tableData" class="display">

            <thead>
            <tr>
                <th>SN</th>
                <th>FILE NAME</th>
                <th>SIZE(Bytes)</th>
                <th>ACTION</th>
            </tr>
            </thead>
            <tbody>
            <%
                int counter = 1;
                for (String[] row : rows) {
            %>
            <tr>
                <td style="vertical-align: middle;"><%=counter%></td>
                <td style="vertical-align: middle;">
                    <%--<a style="color:blue" href="javascript:download('<%=row[0]%>');"><%=row[0]%></a>--%>
                        <%=row[0]%>
                </td>
                <td style="vertical-align: middle;">
                    <%
                        if(row[1].equals("null")){
                    %>
                    0 B
                    <%
                    }
                    else{
                    %>
                    <%--<a style="color:blue" href="javascript:download('<%=row[0]%>');"><%=row[1]%> B</a>--%>
                        <%=row[1]%>
                    <%
                        }
                    %>
                </td>
                <td>
                    <a href="javascript:download('<%=row[0]%>');" title="Download"><img src="img/download.png"  alt="Download"></a>
                    <a href="javascript:deleteFile('<%=row[0]%>');" title="Delete"><img src="img/delete.png" alt="Delete"></a>&nbsp;&nbsp;
                </td>
            </tr>
            <%
                    counter++;
                }
            %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>


