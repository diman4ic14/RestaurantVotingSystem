var dishAjaxUrl = "dishes/"

function vote(id) {
    $.ajax({
        url: dishAjaxUrl + "today/vote/" + id,
        type: "GET",
    }).done(function () {
        successNoty("common.voted")
    })
}

$(function () {
    makeEditable(dishAjaxUrl, {
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "price"
                },
                {
                    "data": "restaurant.name"
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
            "columnDefs": [
                {
                    "targets": "_all",
                    "visible": true
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        }, "#datatable",
        function () {
            $.get(dishAjaxUrl, updateTableByData)
        });
});

$(function () {
    makeEditable(dishAjaxUrl, {
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "price"
                },
                {
                    "data": "restaurant.name"
                },
                {
                    "orderable": false,
                    "defaultContext": "",
                    "render": renderVoteBtn
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        }, "#datatableUser",
        function () {
            $.get(dishAjaxUrl, updateTableByData)
        });
});