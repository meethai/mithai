/**
 * Created by kaustubh on 11/23/16.
 */

$(document).ready(function () {
    // var x = 8;
    // var container = $('#row1');
    //
    // for (i = 0; i < x; i++) {
    //     // container.append($(
    //         // '<div class="col-sm-1 spot-wrapper">' +
    //         //     '<div class="spot ver col-sm-1 spot-wrapper"></div>'
    //             // +
    //             // '<div class="line"></div>' +
    //         // '</div>'
    //         // ));
    //
    // }
    // var container2 = $('#row2');
    // for (i = 0; i < x; i++) {
    //     // container2.append($('<div class="spot ver col-sm-1 spot-wrapper green"></div>'));
    // }

    $(".spot").on('click', function (e) {
        $(e.target).toggleClass("green");
    });

});
