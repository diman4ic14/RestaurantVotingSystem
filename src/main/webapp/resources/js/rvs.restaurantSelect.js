var ajaxUrlRestaurants = 'restaurants/';
var restaurantSelect = $('#restaurantSelect');


function propagateSelect(dataExternal) {
    $.ajax({
            type: 'GET',
            url: ajaxUrlRestaurants,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                var restaurantsArray = [];
                $.each(data, function (i, obj) {
                    restaurantsArray[i] = obj;
                });
                restaurantSelect.empty();
                for (var i = 0; i < restaurantsArray.length; i++) {
                    restaurantSelect.append($("<option></option>").val(restaurantsArray[i].id).html(restaurantsArray[i].name));
                }

                for (var i = 0; i < restaurantsArray.length; i++) {
                    if (data != undefined && restaurantsArray[i].id === dataExternal.restaurant.id) {
                        restaurantSelect.val(dataExternal.restaurant.id);
                        break;
                    }
                    restaurantSelect.val(restaurantsArray[0].id);
                }
                $('#restaurantId').val(restaurantSelect.val());
                restaurantSelect.click(function () {
                    $('#restaurantId').val(restaurantSelect.val());
                })
            }
        }
    );
}