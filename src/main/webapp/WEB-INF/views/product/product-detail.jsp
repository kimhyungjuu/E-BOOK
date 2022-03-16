<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<link rel="stylesheet" href="css/review.css" />

<!-- Breadcrumb Start -->
<div class="breadcrumb-wrap">
	<div class="container-fluid">
		<ul class="breadcrumb">
			<li class="breadcrumb-item"><a href="#">Home</a></li>
			<li class="breadcrumb-item"><a href="#">Products</a></li>
			<li class="breadcrumb-item active">Product Detail</li>
		</ul>
	</div>
</div>
<!-- Breadcrumb End -->

<!-- Product Detail Start -->
<div class="product-detail">
	<div class="container-fluid">
		<div class="row">


			<div class="col-lg-4 sidebar">
				<div class="sidebar-widget brands">
					<h2 class="title">베스트 셀러</h2>
					<ul>
						<li><a href="#">소설</a></li>
						<li><a href="#">경영/경제 </a></li>
						<li><a href="#">인문/사회/역사 </a></li>
						<li><a href="#">자기계발</a></li>
						<li><a href="#">에세이 </a></li>
						<li><a href="#">여행</a></li>
						<li><a href="#">외국어</a></li>
					</ul>
				</div>

				<div class="sidebar-widget tag">
					<h2 class="title">Tags Cloud</h2>
					<a href="#">Lorem ipsum</a> <a href="#">Vivamus</a> <a href="#">Phasellus</a>
					<a href="#">pulvinar</a> <a href="#">Curabitur</a> <a href="#">Fusce</a>
					<a href="#">Sem quis</a> <a href="#">Mollis metus</a> <a href="#">Sit
						amet</a> <a href="#">Vel posuere</a> <a href="#">orci luctus</a> <a
						href="#">Nam lorem</a>
				</div>
			</div>

			<div class="col-lg-8">
				<div class="product-detail-top">
					<div class="row align-items-center">
						<div class="col-md-5">
							<div class="product-slider-single normal-slider slick-initialized slick-slider">
								<div class="slick-list draggable">
									<div class="slick-track" style="opacity: 1; width: 1578px;">
										<img src=ebook-image/${productVO.image} alt="Product Image" />
									</div>
								</div>
							</div>
						</div>

						<div class="col-md-7">
							<div class="product-content">
							
							<div class="title_wrap">
								<div class="title">
									<h2>${productVO.title}</h2>
								</div>
								
								<div class="author">
									<a>${productVO.author} 저 </a>
								</div>	
							</div>
							
								<div class="ratting">
									<i class="fa fa-star"></i> 
									<i class="fa fa-star"></i> 
									<i class="fa fa-star"></i> 
									<i class="fa fa-star"></i> 
									<i class="fa fa-star"></i>
								</div>
								
								<hr>
								
								<div class="price">
								<table class="price_table normal_price_table">
									<tbody>
										<tr>
											<th class="price_title" rowspan="3">구매</th>
										</tr>
										<tr>
											<td class="price_type">전자책 구매</td>
											<td class="book_price">
											<span class="museo_sans discount_rate_renewal  ebook_discount_rate">
											<c:choose>
												<c:when test="${productVO.price eq 0}">
													<span>무료</span>
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="number" maxFractionDigits="3" value="${productVO.price}" /> 원
												</c:otherwise>
											</c:choose>		
											</span>
											</td>
										</tr>
										<tr class="selling_price_row">
											<td class="price_type">전자책 대여</td>
											<td class="book_price">
											<span class="museo_sans discount_rate_renewal">
											<c:choose>
												<c:when test="${productVO.price eq 0}">
													<span>무료</span>
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="number" maxFractionDigits="3" value="${productVO.price_rent}" /> 원
												</c:otherwise>
											</c:choose>
											</span>
											</td>
										</tr>
									</tbody>
								</table>
								</div>

								<div class="action">
									<a class="btn" href="#"><i class="fa fa-heart" onclick="go_wishlist()"></i></a>
									<a class="btn" href="#"><i class="fa fa-shopping-cart" onclick="go_cart()"></i></a> 
									<a class="btn" href="#">구매하기</a>
									<a class="btn" href="#">대여하기</a>
								</div>
							</div>
						</div>
					</div>

					<div class="row product-detail-bottom">
						<div class="col-lg-12">
							<ul class="nav nav-pills nav-justified">
								<li class="nav-item">
									<a class="nav-link active" href="#description">작품소개</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="#specification">목차</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="#reviews">리뷰</a>
								</li>
							</ul>

							<div class="tab-content">
								<div id="description" class="container tab-pane active">
									<h4>Product description</h4>
									<p>${productVO.content }</p>
								</div>
								
								<div id="specification" class="container tab-pane fade">
									<h4>목차</h4>
									<ul style="padding-left: 10px;">
										<li>Lorem ipsum dolor sit amet</li>
										<li>Lorem ipsum dolor sit amet</li>
										<li>Lorem ipsum dolor sit amet</li>
										<li>Lorem ipsum dolor sit amet</li>
										<li>Lorem ipsum dolor sit amet</li>
									</ul>
								</div>
								
								<div id="reviews" class="container tab-pane fade">
									
									<div class="reviews-submit">
										<h4>책에 대한 리뷰를 남겨주세요! </h4>
										<form name="myform" id="myform" method="post" >
											<fieldset>
												<input type="radio" name="rating" value="5" id="rate5">
												<label for="rate5">★</label>
												<input type="radio" name="rating" value="4" id="rate4">
												<label for="rate4">★</label>
												<input type="radio" name="rating" value="3" id="rate3">
												<label for="rate3">★</label>
												<input type="radio" name="rating" value="2" id="rate2">
												<label for="rate2">★</label>
												<input type="radio" name="rating" value="1" id="rate1">
												<label for="rate1">★</label>
											</fieldset>
											<div class="row form">
												<div class="col-sm-12">
													<textarea placeholder="리뷰를 남겨주세요" id="review"></textarea>
												</div>
												<div class="col-sm-12">
													<input type="button" value="리뷰작성" class="submit" id="review" onclick="location.href='post_save'">												
												</div>
											</div>
										</form>
									</div>
									<div class="reviews-submitted">
										<c:forEach items="${reviewList}" var="reviewVO">
										
										<div class="reviewer">
											${reviewVO.id} &nbsp; 
											<span>${reviewVO.regdate}</span>
										</div>
										<a href="#" onclick="go_review_delete()">삭제</a>
										<div class="ratting ${reviewVO.rating}"></div>
										<p>${reviewVO.content}</p>
									
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Product Detail End -->
<%@ include file="../footer.jsp"%>