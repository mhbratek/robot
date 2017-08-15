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
			<div class="col-sm-6 col-md-3" style="padding-bottom:  15px">
				<h2><a href="/robot/books"> Books</a></h2>
			</div>
			<div class="col-sm-6 col-md-3" style="padding-bottom:  15px">
				<h2>
					<a href="/robot/addBooks">
						<span class="glyphicon glyphicon-download glyphicon"></span> add fake data to database
					</a>
				</h2>
			</div>
		</section>
	</body>
</html>