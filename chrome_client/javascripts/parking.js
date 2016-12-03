/**
 * Created by kaustubh on 11/23/16.
 */

$(document).ready(function () {
    $(".spot").on('click', function (e) {
        $(e.target).toggleClass("green");
    });
    var spots;

    function getSpots(func) {
        $.get("http://localhost:3000/lots/allspots", function (d, s) {
            console.log("Status: " + s);
            spots = JSON.parse(d);
            func();
        });
    }

    var stop = false;
    //TODO use cache control
    function updateSpotsIfChanged() {
        console.log('updating spots if changed');
        var prev = JSON.stringify(spots);
        getSpots(function () {
            if (!_.isEqual(JSON.stringify(spots), prev)) {
                console.log(spots);
                $('#parking .spot').each(function (i, e) {
                    console.log($(this).attr('id'));
                    if (spots[$(this).attr('id')] > 0)
                        $('#' + $(this).attr('id')).addClass('green');
                    else
                        $('#' + $(this).attr('id')).removeClass('green');
                });
            }
        });

        setTimeout(function () {
            if (!stop) updateSpotsIfChanged();
        }, 30000);
    }

    updateSpotsIfChanged();

});
