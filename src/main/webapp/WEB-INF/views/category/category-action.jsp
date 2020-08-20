<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored = "false" %>
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<c:if test="${category.id != null}">
				<h2>Edit</h2>
				</c:if>
				<c:if test="${category.id == null}">
					<h2>Add</h2>
				</c:if>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">

					<div class="x_content">
						<br />
						<form:form modelAttribute="category" cssClass="form-horizontal form-label-left"
								   action="${pageContext.request.contextPath}/product/category/save" method="POST">
							<form:hidden path="id" />
							<form:hidden path="activeFlag" />
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="code">Code <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="code" cssClass="form-control col-md-7 col-xs-12"  />
									<div class="has-error">
										<form:errors path="code" cssClass="help-block"></form:errors>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Name <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="name" cssClass="form-control col-md-7 col-xs-12" />
									<div class="has-error">
										<form:errors path="name" cssClass="help-block"></form:errors>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="description" class="control-label col-md-3 col-sm-3 col-xs-12">Description</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="description" cssClass="form-control col-md-7 col-xs-12"  />
									<div class="has-error">
										<form:errors path="description" cssClass="help-block"></form:errors>
									</div>
								</div>
							</div>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
									<button class="btn btn-primary" type="button" onclick="cancel();">Cancel</button>

										<button class="btn btn-primary" type="reset">Reset</button>
										<button type="submit" class="btn btn-success">Submit</button>

								</div>
							</div>

						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(
			function() {
				$('#categorylistId').addClass('current-page').siblings()
						.removeClass('current-page');
				var parent = $('#categorylistId').parents('li');
				parent.addClass('active').siblings().removeClass('active');
				$('#categorylistId').parents().show();
			});
	function cancel() {
		window.location.href = '<c:url value="/product/category/list"/>'
	}
</script>