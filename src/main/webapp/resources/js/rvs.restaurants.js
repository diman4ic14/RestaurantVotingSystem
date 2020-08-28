var restaurantAjaxUrl = "restaurants/"
var restaurantToAjaxUrl = "restaurants/today/"

// $(function () {
//     makeEditable(restaurantToAjaxUrl, {
//             "columns": [
//                 {
//                     "data": "name"
//                 },
//                 {
//                     "data": "votes"
//                 }
//             ],
//             "order": [
//                 [
//                     0,
//                     "asc"
//                 ]
//             ]
//         }, "#datatableUser",
//         function () {
//             $.get(restaurantAjaxUrl, updateTableByData)
//         });
// });

$(function () {
    makeEditable(restaurantAjaxUrl, {
            "columns": [
                {
                    "data": "name"
                },
                {
                    "orderable": false,
                    "defaultContext": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContext": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        },
        "#datatable",
        function () {
            $.ajax(restaurantAjaxUrl, updateTableByData)
        })
})