const mVar = 45;
const margin = {top: mVar, right: mVar, bottom: mVar, left: mVar};
const width = window.innerWidth - margin.left - margin.right - mVar / 2;
const height = window.innerHeight - margin.top - margin.bottom - mVar / 2;

const svg = d3.select("#visualization")
    .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")").attr("class", "graph-svg-component");

const x = d3.scaleBand().range([0, width]);

const y = d3.scaleLinear().range([height, 0]);


d3.json('http://localhost:8080/graphApi').then((data) => {
    data.forEach((d) => {
        d.price = +d.price;
        d.count = +d.count;
    })

    x.domain(data.map((d) => {
        return d.price
    })).padding(0.1);

    y.domain([0, d3.max(data, (d) => {
        return d.count;
    })]);


    svg.append('g')
        .selectAll("dot")
        .data(data)
        .enter()
        .append("circle")
        .attr("cx", (d) => {
            return x(d.price);
        })
        .attr("width", x.bandwidth())
        .attr("cy", (d) => {
            return y(d.count)
        })
        .attr("height", (d) => {
            return height - y(d.count)
        })

    svg.selectAll(".bar")
        .data(data)
        .enter().append("rect")
        .attr("class", "bar")
        .attr("x", (d) => {
            return x(d.price);
        })
        .attr("width", x.bandwidth())
        .attr("y", (d) => {
            return y(d.count)
        })
        .attr("height", (d) => {
            return height - y(d.count)
        }).style("fill", "#006B38FF")
        .style("stroke", "#101820FF")

    svg.append("g")
        .attr("transform", `translate(0, ${height})`)
        .call(d3.axisBottom(x));

    svg.append("g")
        .call(d3.axisLeft(y));


}).catch((error) => {
    console.error(error);
});