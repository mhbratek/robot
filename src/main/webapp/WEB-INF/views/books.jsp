<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
		<title>Witaj</title>
	</head>
	<body>
		<section>
			<div class="jumbotron">
				<div class="container">
					<h1> ${start} </h1>
					<p> ${content} </p>
				</div>
			</div>
			<div class="row">
				<h2>  Today's promotions: </h2>
				<c:forEach items="${books}" var="book">
					<div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
						<div class="thumbnail">
							<div class="caption">
								<h3>${book.title}</h3>
								<p>${book.id}</p>
								<p>${book.author}</p>
								<p>${book.genre}</p>
								<p>${book.promoDetails}</p>
								<p>${book.price} PLN</p>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</section>
	</body>
</html>