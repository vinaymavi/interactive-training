<%--
  User: @vinaymavi
  Date: 3/18/18
  Time: 11:24 AM
  This file will display live result of session with firebase.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <title><c:out value="${name}"/>-Live Result</title>
    <style>
        html,
        body {
            padding: 0;
            margin: 0;
            box-sizing: border-box;
            font-family: Helvetica, Calibri, Roboto, Open Sans, sans-serif
            -webkit-backface-visibility: hidden;
        }

        * {
            box-sizing: inherit;
        }

        h1 {
            text-align: center;
        }

        svg {
            margin: auto;
            display: block;
        }

        .circle-overlay {
            font-size: 16px;
            border-radius: 50%;
            position: absolute;
            overflow: hidden;
            /*it's buggy with the foreignObject background right now*/
            /*background-color: rgba(255,255,255,0.5);*/
        }

        .circle-overlay__inner {
            text-align: center;
            width: 100%;
            height: 100%;
        }

        .hidden {
            display: none;
        }

        .node-icon--faded {
            opacity: 0.5;
        }

        svg {
            width: 100%;
            height: 700px;
        }
    </style>
    <script>
        const sessionid = '<c:out value="${id}"/>';
    </script>
</head>

<body>
<h1>Git-Essentails Live Quiz </h1>
<button id="set1" onclick="render(data1)">Set1</button>
<button id='set2' onclick="render(data2)">Set2</button>

<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/4.13.0/d3.js"></script>

<script src="https://www.gstatic.com/firebasejs/4.11.0/firebase.js"></script>
<script>
    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyBNyMER3IZ51HuOSl4ZK8RzIW7pctq9uDM",
        authDomain: "slides-prod.firebaseapp.com",
        databaseURL: "https://slides-prod.firebaseio.com",
        projectId: "slides-prod",
        storageBucket: "slides-prod.appspot.com",
        messagingSenderId: "293774696802"
    };
    firebase.initializeApp(config);
    var database = firebase.database();
    console.log(database);
    database.ref('live_results').on('value',(snapshot)=>{
        console.log(snapshot.val());
    });
</script>
<script>
    let data1 = [
        {
            cat: 'backend', name: 'Keystone CMS', value: 50,
            icon: 'https://avatars0.githubusercontent.com/u/1610828?s=460&v=4',
            desc: `
				The de-facto CMS system for website built with Node.js. It can be compared with
				Wordpress of PHP language.
			`
        }, {
            cat: 'backend', name: 'KoaJS', value: 10,
            icon: 'https://avatars0.githubusercontent.com/u/1610828?s=460&v=4',
            desc: `
				The advanced and improved version of ExpressJS, with leaner middlewares architecture
				thanks to the avent of ES6 generators.
			`
        }];

    let data2 = [
        {
            cat: 'library', name: 'D3', value: 30,
            icon: 'https://avatars0.githubusercontent.com/u/1610828?s=460&v=4',
            desc: `
				D3.js (or just D3 for Data-Driven Documents) is a JavaScript library for
				producing dynamic, interactive data visualizations in web browsers.
				It makes use of the widely implemented SVG, HTML5, and CSS standards.<br>
				This infographic you are viewing is made with D3.
			`
        }, {
            cat: 'library', name: 'Raphaël', value: 10,
            icon: 'https://avatars0.githubusercontent.com/u/1610828?s=460&v=4',
            desc: `
				Raphaël is a cross-browser JavaScript library that draws Vector graphics for web sites.
				It will use SVG for most browsers, but will use VML for older versions of Internet Explorer.
			`
        }]
</script>

<script>
    let svg;
    let width = document.body.clientWidth; // get width in pixels
    let height;
    let centerX = width * 0.5;
    let centerY;
    let strength = 0.05;
    let focusedNode;
    let isFirst = true;
    let node;

    function createSvg() {
        document.querySelector('svg') && document.querySelector('svg').remove();
        svg = d3.select("body").append("svg").attr("width", 1200)
            .attr("height", 700);
        node && node.exit().remove();
    }

    function render(data) {
        createSvg();
        height = +svg.attr('height');
        centerY = height * 0.5;

        let format = d3.format(',d');
        let scaleColor = d3.scaleOrdinal(d3.schemeCategory20);

        // use pack to calculate radius of the circle
        let pack = d3.pack()
            .size([width, height])
            .padding(1.5);

        let forceCollide = d3.forceCollide(d => d.r + 1);

        // use the force
        let simulation = d3.forceSimulation()
        // .force('link', d3.forceLink().id(d => d.id))
            .force('charge', d3.forceManyBody())
            .force('collide', forceCollide)
            // .force('center', d3.forceCenter(centerX, centerY))
            .force('x', d3.forceX(centerX).strength(strength))
            .force('y', d3.forceY(centerY).strength(strength));

        let root = d3.hierarchy({children: data})
            .sum(d => d.value);
        // we use pack() to automatically calculate radius conveniently only
        // and get only the leaves
        let nodes = pack(root).leaves().map(node => {
            console.log('node:', node.x, (node.x - centerX) * 2);
            const data = node.data;
            return {
                x: centerX + (node.x - centerX) * 3, // magnify start position to have transition to center movement
                y: centerY + (node.y - centerY) * 3,
                r: 0, // for tweening
                radius: node.r, //original radius
                id: data.cat + '.' + (data.name.replace(/\s/g, '-')),
                cat: data.cat,
                name: data.name,
                value: data.value,
                icon: data.icon,
                desc: data.desc,
            }
        });
        simulation.nodes(nodes).on('tick', ticked);
        svg.style('background-color', '#eee');
        node = svg.selectAll('.node')
            .data(nodes)
            .enter().append('g')
            .attr('class', 'node')
            .call(d3.drag()
                .on('start', (d) => {
                    if (!d3.event.active) simulation.alphaTarget(0.2).restart();
                    d.fx = d.x;
                    d.fy = d.y;
                })
                .on('drag', (d) => {
                    d.fx = d3.event.x;
                    d.fy = d3.event.y;
                })
                .on('end', (d) => {
                    if (!d3.event.active) simulation.alphaTarget(0);
                    d.fx = null;
                    d.fy = null;
                }));

        node.append('circle')
            .attr('id', d => d.id)
            .attr('r', 0)
            .style('fill', d => scaleColor(d.cat))
            .transition().duration(2000).ease(d3.easeElasticOut)
            .tween('circleIn', (d) => {
                let i = d3.interpolateNumber(0, d.radius);
                return (t) => {
                    d.r = i(t);
                    simulation.force('collide', forceCollide);
                }
            });

        node.append('clipPath')
            .attr('id', d => "clip-"+d.id)
            .append('use')
            .attr('xlink:href', d => "#"+d.id);

        // display image as circle icon
        node.filter(d => String(d.icon))
            .append('image')
            .classed('node-icon', true)
            .attr('clip-path', d => "url(#clip-"+d.id+")")
            .attr('xlink:href', d => d.icon)
            .attr('x', d => -d.radius * 0.7)
            .attr('y', d => -d.radius * 0.7)
            .attr('height', d => d.radius * 2 * 0.7)
            .attr('width', d => d.radius * 2 * 0.7)

        node.append('title')
            .text(d => (d.cat + '::' + d.name + '\n' + format(d.value)));

        let infoBox = node.append('foreignObject')
            .classed('circle-overlay hidden', true)
            .attr('x', -350 * 0.5 * 0.8)
            .attr('y', -350 * 0.5 * 0.8)
            .attr('height', 350 * 0.8)
            .attr('width', 350 * 0.8)
            .append('xhtml:div')
            .classed('circle-overlay__inner', true);

        infoBox.append('h2')
            .classed('circle-overlay__title', true)
            .text(d => d.name);

        infoBox.append('p')
            .classed('circle-overlay__body', true)
            .html(d => d.desc);


        node.on('click', (currentNode) => {
            d3.event.stopPropagation();
            console.log('currentNode', currentNode);
            let currentTarget = d3.event.currentTarget; // the <g> el

            if (currentNode === focusedNode) {
                // no focusedNode or same focused node is clicked
                return;
            }
            let lastNode = focusedNode;
            focusedNode = currentNode;

            simulation.alphaTarget(0.2).restart();
            // hide all circle-overlay
            d3.selectAll('.circle-overlay').classed('hidden', true);
            d3.selectAll('.node-icon').classed('node-icon--faded', false);

            // don't fix last node to center anymore
            if (lastNode) {
                lastNode.fx = null;
                lastNode.fy = null;
                node.filter((d, i) => i === lastNode.index)
                    .transition().duration(2000).ease(d3.easePolyOut)
                    .tween('circleOut', () => {
                        let irl = d3.interpolateNumber(lastNode.r, lastNode.radius);
                        return (t) => {
                            lastNode.r = irl(t);
                        }
                    })
                    .on('interrupt', () => {
                        lastNode.r = lastNode.radius;
                    });
            }

            // if (!d3.event.active) simulation.alphaTarget(0.5).restart();

            d3.transition().duration(2000).ease(d3.easePolyOut)
                .tween('moveIn', () => {
                    console.log('tweenMoveIn', currentNode);
                    let ix = d3.interpolateNumber(currentNode.x, centerX);
                    let iy = d3.interpolateNumber(currentNode.y, centerY);
                    let ir = d3.interpolateNumber(currentNode.r, centerY * 0.5);
                    return function (t) {
                        // console.log('i', ix(t), iy(t));
                        currentNode.fx = ix(t);
                        currentNode.fy = iy(t);
                        currentNode.r = ir(t);
                        simulation.force('collide', forceCollide);
                    };
                })
                .on('end', () => {
                    simulation.alphaTarget(0);
                    let $currentGroup = d3.select(currentTarget);
                    $currentGroup.select('.circle-overlay')
                        .classed('hidden', false);
                    $currentGroup.select('.node-icon')
                        .classed('node-icon--faded', true);

                })
                .on('interrupt', () => {
                    console.log('move interrupt', currentNode);
                    currentNode.fx = null;
                    currentNode.fy = null;
                    simulation.alphaTarget(0);
                });

        });

        function ticked() {
            node
                .attr('transform', d =>{ return "translate("+d.x+","+d.y+")"})
                .select('circle')
                .attr('r', d => d.r);
        }

        // blur
        d3.select(document).on('click', () => {
            let target = d3.event.target;
            // check if click on document but not on the circle overlay
            if (!target.closest('#circle-overlay') && focusedNode) {
                focusedNode.fx = null;
                focusedNode.fy = null;
                simulation.alphaTarget(0.2).restart();
                d3.transition().duration(2000).ease(d3.easePolyOut)
                    .tween('moveOut', function () {
                        console.log('tweenMoveOut', focusedNode);
                        let ir = d3.interpolateNumber(focusedNode.r, focusedNode.radius);
                        return function (t) {
                            focusedNode.r = ir(t);
                            simulation.force('collide', forceCollide);
                        };
                    })
                    .on('end', () => {
                        focusedNode = null;
                        simulation.alphaTarget(0);
                    })
                    .on('interrupt', () => {
                        simulation.alphaTarget(0);
                    });

                // hide all circle-overlay
                d3.selectAll('.circle-overlay').classed('hidden', true);
                d3.selectAll('.node-icon').classed('node-icon--faded', false);
            }
        });
    }
</script>
</body>

</html>
