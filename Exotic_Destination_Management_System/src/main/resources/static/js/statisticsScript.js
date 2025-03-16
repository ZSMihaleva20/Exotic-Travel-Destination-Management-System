document.querySelector("#userMenu").addEventListener("click", () => {
    document.getElementById("dropdownMenu").classList.toggle("hidden");
});

document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.remove("opacity-0");
});

const ctx = document.getElementById('rating-chart').getContext('2d');
const popularityCtx = document.getElementById('popularity-chart').getContext('2d');

// Define different colors for each column dynamically
const backgroundColors = [
    '#FF5733', '#33FF57', '#3357FF', '#FF33A1', '#F4D03F', '#8E44AD', '#16A085',
    '#E67E22', '#1ABC9C', '#D35400', '#2ECC71', '#2980B9', '#C0392B', '#7D3C98'
];

// Generate colors dynamically if there are more destinations than colors in the array
const barColors = destinationLabels.map((_, index) => backgroundColors[index % backgroundColors.length]);

new Chart(ctx, {
    type: 'bar',
    data: {
        labels: destinationLabels,
        datasets: [{
            label: 'Среден рейтинг за всяка дестинация',
            data: ratings,
            backgroundColor: barColors, // Assign colors dynamically
            borderColor: barColors.map(color => color.replace('1)', '0.8)')), // Slightly darker border
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true
            }
        },
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem) {
                        // Customize tooltip label text
                        const value = tooltipItem.raw;
                        return `Среден рейтинг: ${value} звезди`; // Customize how the text should appear
                    }
                }
            },
            legend: {
                display: false
            },
            title: {  // ✅ Added title above the bar chart
                display: true,
                text: 'Среден рейтинг за всяка дестинация',
                font: {
                    size: 18,
                    weight: 'bold'
                },
                padding: {
                    top: 10,
                    bottom: 20
                }
            },
        }
    }
});


new Chart(popularityCtx, {
    type: 'pie',
    data: {
        labels: destinationPopularityLabels,
        datasets: [{
            label: 'Популярност на дестинациите', // Not visible by default, so we add a title
            data: popularityData,
            backgroundColor: barColors,
            borderColor: '#fff',
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        plugins: {
            // ✅ Add title above the chart
            title: {
                display: true,
                text: 'Популярност на дестинациите',
                font: {
                    size: 18,
                    weight: 'bold'
                },
                padding: {
                    top: 10,
                    bottom: 20
                }
            },
            legend: {
                position: 'bottom',
                labels: {
                    font: {
                        size: 14
                    }
                }
            },
            tooltip: {
                callbacks: {
                    label: function(tooltipItem) {
                        let value = tooltipItem.raw;
                        let total = popularityData.reduce((a, b) => a + b, 0);
                        let percentage = ((value / total) * 100).toFixed(1);

                        return `Брой резервирали: ${value} (${percentage}%)`;
                    }
                }
            },
            datalabels: {
                color: '#fff',
                font: {
                    size: 14,
                    weight: 'bold'
                },
                formatter: (value, ctx) => {
                    let total = ctx.dataset.data.reduce((a, b) => a + b, 0);
                    let percentage = ((value / total) * 100).toFixed(1);
                    return `${percentage}%`; // Show percentage inside the chart
                },
                anchor: 'center',
                align: 'start',
                offset: 10
            }
        }
    }
});