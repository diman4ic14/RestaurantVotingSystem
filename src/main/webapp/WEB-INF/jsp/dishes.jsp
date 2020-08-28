<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/rvs.common.js" defer></script>
<script type="text/javascript" src="resources/js/rvs.dishes.js" defer></script>
<script type="text/javascript" src="resources/js/rvs.restaurantSelect.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="dish.title"/></h3>

        <sec:authorize access="hasRole('ADMIN')">
            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="common.add"/>
            </button>
            <table class="table table-striped" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="dish.name"/></th>
                    <th><spring:message code="dish.price"/></th>
                    <th><spring:message code="dish.restaurant"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
            </table>
        </sec:authorize>

        <sec:authorize access="hasRole('USER')">
            <table class="table table-striped" id="datatableUser">
                <thead>
                <tr>
                    <th><spring:message code="dish.name"/></th>
                    <th><spring:message code="dish.price"/></th>
                    <th><spring:message code="dish.restaurant"/></th>
                    <th></th>
                </tr>
                </thead>
            </table>
        </sec:authorize>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="col-form-label"><spring:message code="dish.name"/></label>
                        <input type="text" class="form-control" id="name" name="name"
                            placeholder="<spring:message code="dish.name"/>">
                    </div>

                    <div class="form-group">
                        <label for="price" class="col-form-label"><spring:message code="dish.price"/></label>
                        <input type="number" class="form-control" id="price" name="price"
                            placeholder="<spring:message code="dish.price"/>">
                    </div>

                    <div class="form-group">
                        <label for="restaurantSelect" class="custom-control-label col-xs-3">
                            <spring:message code="dish.restaurant"/></label>

                        <div class="form-control" id="restaurantSelect" name="restaurant">
                            <option value="" disabled selected>restaurant.name</option>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="dish"/>
</jsp:include>
</html>