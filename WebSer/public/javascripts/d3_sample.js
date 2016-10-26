/**
 * Created by kaustubh on 10/21/16.
 */

var svg = d3.select("svg"),
    width = +svg.attr("width"),
    height = +svg.attr("height");
console.log("w= " + width + " h= " + height);
var color = d3.scaleOrdinal(d3.schemeCategory20);

var simulation = d3.forceSimulation()
    .force("link", d3.forceLink().id(function (d) {
        return d.id;
    }))
    .force("charge", d3.forceManyBody())
    .force("center", d3.forceCenter(800 / 4, 400 / 4));

// d3.json("miserables.json", function (error, graph) {
var graph = data;
// if (error) throw error;
console.log(data);
var link = svg.append("g")
    .attr("class", "links")
    .selectAll("line")
    .data(graph.links)
    .enter().append("line")
    .attr("transform", "scale(2)")
    .attr("stroke-width", function (d) {
        return Math.sqrt(d.value);
    });

var node = svg.append("g")
    .attr("class", "nodes")
    .selectAll("g")
    .data(graph.nodes)
    .enter()
    .append("g")
    .attr("class", "g-wrap")
    .append("circle")
    .attr("r", 5)
    .attr("transform", "scale(2)")
    .attr("fill", function (d) {
        return color(d.group);
    })
    .call(d3.drag()
        .on("start", dragstarted)
        .on("drag", dragged)
        .on("end", dragended));

svg.selectAll(".g-wrap")
    .append("svg:text")
    .text(function (d) {
        return d.id;
    })
    .style("fill", "#555")
    .style("font-family", "Arial")
    .style("font-size", 12);

var attractForce = d3.forceManyBody().strength(100).distanceMax(2)
    .distanceMin(20);
var repelForce = d3.forceManyBody().strength(-240).distanceMax(50)
    .distanceMin(40);

simulation
    .nodes(graph.nodes)
    .alphaDecay(0.05)
    .force("attractForce", attractForce)
    .force("repelForce", repelForce)
    .on("tick", ticked);

simulation.force("link")
    .links(graph.links);

function ticked() {
    link
        .attr("x1", function (d) {
            return d.source.x;
        })
        .attr("y1", function (d) {
            return d.source.y;
        })
        .attr("x2", function (d) {
            return d.target.x;
        })
        .attr("y2", function (d) {
            return d.target.y;
        });

    node
        .attr("cx", function (d) {
            return d.x;
        })
        .attr("cy", function (d) {
            return d.y;
        });

    svg.selectAll("text")
        .attr("x", function (d) {
            return d.x;
        })
        .attr("y", function (d) {
            return d.y;
        }).attr("transform", "scale(2)");
}
// });

function dragstarted(d) {
    if (!d3.event.active) simulation.alphaTarget(0.3).restart();
    d.fx = d.x;
    d.fy = d.y;
}

function dragged(d) {
    d.fx = d3.event.x;
    d.fy = d3.event.y;
}

function dragended(d) {
    if (!d3.event.active) simulation.alphaTarget(0);
    d.fx = null;
    d.fy = null;
    d.fy = null;
}