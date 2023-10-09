import React, { useState, useEffect, useRef } from 'react';

export default function TodoStats(props) {
    const ref = useRef(null);
    const chartRef = useRef(null);
    const buildChart = (node) => {
        chartRef.current = new Chart(node, {
            type: 'bar',
            data: {
                labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
                datasets: [{
                    label: '# of Votes',
                    data: [12, 19, 3, 5, 2, 3],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    useEffect(() => {
        buildChart(ref.current);
        return () => {
            chartRef.current.destroy();
        }
    }, [])

    return (
        <canvas ref={ref} style={{ height: 200}}>start</canvas>
    );
}