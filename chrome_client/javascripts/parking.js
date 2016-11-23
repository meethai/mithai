/**
 * Created by kaustubh on 11/23/16.
 */

$(document).ready(function () {
    var x = 8;
    var container = $('#row1');

    for (i = 0; i < x; i++) {
        container.append($('<div class="spot ver col-sm-1"></div>'));

    }
    var container2 = $('#row2');
    for (i = 0; i < x; i++) {
        container2.append($('<div class="spot green ver col-sm-1"></div>'));
    }

    $(".spot").on('click', function (e) {
        $(e.target).toggleClass("green");
    });

});
