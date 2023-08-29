<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width" />
<script type="text/javascript">
    
</script>
<c:set var="titleKey" value="title.welcome.home" />
<title><spring:message code="${titleKey}" text="projectName" /></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div class="container">
    <jsp:include page="../layout/header.jsp"  />
        <div id="wrapper">
            <h1 id="title">Hello world!</h1>
            <p>The time on the server is ${serverTime}.</p>
        </div>
    <jsp:include page="../layout/footer.jsp"  />
    </div>
</body>
</html>