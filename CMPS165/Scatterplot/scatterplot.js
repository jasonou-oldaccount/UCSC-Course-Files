//Define Margin
var margin = {left: 80, right: 80, top: 50, bottom: 50 }, 
    width = 960 - margin.left -margin.right,
    height = 500 - margin.top - margin.bottom;

//Define Scales   
var x = d3.scale.linear()
    .domain([0,16]) //Need to redefine this after loading the data
    .range([0, width]);

var y = d3.scale.linear()
    .domain([-10,400]) //Need to redfine this after loading the data
    .range([height, 0]);

//Define Tooltip here
var tooltip = d3.select("body").append("div")   
    .attr("class", "tooltip")               
    .style("opacity", 0);

//Zoom Var
var zoom = d3.behavior.zoom()
        .x(x)
        .y(y)
        .scaleExtent([1, 32])
        .on("zoom", zoomed);

//Define SVG
var svg = d3.select("body")
    .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
    .call(zoom);

//Define Axis
var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom")
    .tickSize(-height);

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .ticks(5)
    .tickSize(-width);

//Zoom Function
function zoomed() {
    svg.selectAll(".dot")
        .attr("cx", function(d) {return x(d.gdp);})
        .attr("cy", function(d) {return y(d.epc);})
    svg.select(".x.axis").call(xAxis)
    svg.select(".y.axis").call(yAxis)
}

//Get Data
d3.csv("scatterdata.csv", function(error, data) {
    
    //Define Color
    var colors = d3.scale.category20();
    colors.domain(d3.keys(data[0]).filter(function(key) { return key !== "country"; }));
    
    data.forEach(function(d) {
        d.country = d.country;
        d.gdp = +d.gdp;
        d.population = +d.population;
        d.epc = +d.epc;
        d.total = +d.total;
    });
    
    svg.append("rect")
        .attr("width", width)
        .attr("height", height)
        .attr("fill", "LightCyan");

    //Scale Changes as we Zoom
    // Call the function d3.behavior.zoom to Add zoom

    //Draw Scatterplot
    svg.selectAll(".dot")
        .data(data)
        .enter().append("circle")
        .attr("class", "dot")
        .attr("r", function(d) { return Math.sqrt(d.total)/.2; })
        .attr("cx", function(d) {return x(d.gdp);})
        .attr("cy", function(d) {return y(d.epc);})
        .style("fill", function (d) { return colors(d.country); })
        .on("mouseover", function(d) {
            tooltip .transition()
                    .duration(500)
                    .style("opacity", .9);
            tooltip .html("<strong><u>" + d.country + "</u></strong>"+ "<br/>"
                          + "<strong>Population : </strong>" + d.population + " Million" + "<br/>"
                          + "<strong>GDP : </strong>" + d.gdp + " Trillion" + "<br/>"
                          + "<strong>EPC : </strong>" + d.epc + " Million BTUs" + "<br/>"
                          + "<strong>TEC : </strong>" + d.total + " Trillion BTUs")
                    .style("left", (d3.event.pageX) + "px")
                    .style("top", (d3.event.pageY - 28) + "px");
            })
        .on("mouseout", function(d) {
            tooltip .transition()        
                    .duration(500)      
                    .style("opacity", 0);
        });

    //x-axis
    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .call(xAxis)
        .append("text")
        .attr("class", "label")
        .attr("y", 50)
        .attr("x", width/2)
        .style("text-anchor", "end")
        .attr("font-size", "16px")
        .text("GDP (in Trillion US Dollars) in 2010");


    //Y-axis
    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis)
        .append("text")
        .attr("class", "label")
        .attr("transform", "rotate(-90)")
        .attr("y", -50)
        .attr("x", -50)
        .attr("dy", ".71em")
        .style("text-anchor", "end")
        .attr("font-size", "16px")
        .text("Energy Consumption per Capita (in Million BTUs per person)");

    // draw legend colored rectangles
    svg.append("rect")
        .attr("x", width-250)
        .attr("y", height-190)
        .attr("width", 220)
        .attr("height", 180)
        .attr("fill", "lightgrey")
        .style("stroke-size", "1px");

    svg.append("circle")
        .attr("r", 5)
        .attr("cx", width-100)
        .attr("cy", height-175)
        .style("fill", "white");

    svg.append("circle")
        .attr("r", 15.8)
        .attr("cx", width-100)
        .attr("cy", height-150)
        .style("fill", "white");

    svg.append("circle")
        .attr("r", 50)
        .attr("cx", width-100)
        .attr("cy", height-80)
        .style("fill", "white");

    svg.append("text")
        .attr("class", "label")
        .attr("x", width -150)
        .attr("y", height-172)
        .style("text-anchor", "end")
        .text(" 1 Trillion BTUs");

    svg.append("text")
        .attr("class", "label")
        .attr("x", width -150)
        .attr("y", height-147)
        .style("text-anchor", "end")
        .text(" 10 Trillion BTUs");

    svg.append("text")
        .attr("class", "label")
        .attr("x", width -150)
        .attr("y", height-77)
        .style("text-anchor", "end")
        .text(" 100 Trillion BTUs");

    svg.append("text")
        .attr("class", "label")
        .attr("x", width -150)
        .attr("y", height-15)
        .style("text-anchor", "middle")
        .style("fill", "Green") 
        .attr("font-size", "20px")
        .text("Total Energy Consumption");
});