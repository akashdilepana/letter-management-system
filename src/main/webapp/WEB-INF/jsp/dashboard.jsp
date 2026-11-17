<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="JSPF/header.jsp" %>
    </head>

    <body class="bg-light">
        <style>
            .profile-card {
                background-color: #3e549b; /* Changed from #1abc9c */
                color: white;
                padding: 20px;
                border-radius: 8px;
            }
            .section-box {

                padding: 15px;
                border-radius: 8px;
                margin-bottom: 15px;
                box-shadow: 0 0 5px rgba(0,0,0,0.1);
            }

            .bg-pink {
                background-color: #FADADD;
            }
            .bg-orange {
                background-color: #FCDA53;
            }
            .bg-red {
                background-color: #FC9F53;
            }
            .bg-dred {
                background-color: #F0432C;
            }
            .bg-blue {
                background-color: #DDEBF7;
            }
            .bg-light {
                background-color: #F7E1FE;
            }
            .bg-teal {
                background-color: #A3D5D3;
            }

            .profile-pic {
                width: 100px;
                height: 100px;
                object-fit: cover;
                border-radius: 50%;
                border: 3px solid #fff;
            }
        </style>
        <%@include file="JSPF/loader.jsp" %>
        <div id="pcoded" class="pcoded">
            <div class="pcoded-overlay-box"></div>
            <div class="pcoded-container navbar-wrapper">
                <%@include file="JSPF/navbar.jsp"%>
                <div class="pcoded-main-container">
                    <div class="pcoded-wrapper">
                        <%@include file="JSPF/sidenav.jsp"%>
                        <div class="pcoded-content">
                            <div class="pcoded-inner-content">
                                <div class="main-body">
                                    <div class="page-wrapper">
                                        <div class="page-body">

                                            <div class="container-fluid mb-4">
                                                <div class="row mb-3">
                                                    <div class="col-md-4 mb-3">
                                                        <div class="card text-white bg-primary h-100">
                                                            <div class="card-body">
                                                                <h5 class="card-title">All Letters</h5>
                                                                <h2 id="allLettersCount">—</h2>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4 mb-3">
                                                        <div class="card text-white bg-success h-100">
                                                            <div class="card-body">
                                                                <h5 class="card-title">Replied Letters</h5>
                                                                <h2 id="repliedLettersCount">—</h2>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4 mb-3">
                                                        <div class="card text-white bg-warning h-100">
                                                            <div class="card-body">
                                                                <h5 class="card-title">Not Replied</h5>
                                                                <h2 id="notRepliedLettersCount">—</h2>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row mb-3">
                                                    <div class="col-12 text-right">
                                                        <a class="btn btn-outline-primary mr-2" href="<%=request.getContextPath()%>/letters">View Letters</a>
                                                        <a class="btn btn-primary" href="<%=request.getContextPath()%>/letters?new=1">+ Add Letter</a>
                                                    </div>
                                                </div>
                                                <div class="card">
                                                    <div class="card-header d-flex justify-content-between align-items-center">
                                                        <h5 class="mb-0">Letter Activity</h5>
                                                        <select id="letterChartPeriod" class="form-control" style="max-width: 150px" aria-label="Chart time range">
                                                            <option value="day">Day</option>
                                                            <option value="week">Week</option>
                                                            <option value="month">Month</option>
                                                        </select>
                                                    </div>
                                                    <div class="card-body" style="height: 340px">
                                                        <canvas id="letterActivityChart" aria-label="Letters by reply status" role="img"></canvas>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="JSPF/scripts.jsp" %>
        <script type="text/javascript" src="files/js/slimselect.js"></script>
        <script type="text/javascript" src="files/js/datatables.min.js"></script>
        <script type="text/javascript" src="files/js/sweetalert2.js"></script>
        <script type="text/javascript" src="files/js/func.js"></script>
        <script type="text/javascript" src="files/js/autoNumeric.js"></script>
        <script type="text/javascript" src="files/js/dataTables.responsive.min.js"></script>
        <script type="text/javascript" src="files/js/Chart.js"></script>
        <script>
            const dashboardBase = '<%=request.getContextPath()%>/dashboard';
            let letterActivityChart;

            fetch(dashboardBase + '/letter-stats')
                .then(response => {
                    if (!response.ok) throw new Error('Could not load letter counts.');
                    return response.json();
                })
                .then(counts => {
                    $('#allLettersCount').text(counts.allLetters);
                    $('#repliedLettersCount').text(counts.repliedLetters);
                    $('#notRepliedLettersCount').text(counts.notRepliedLetters);
                })
                .catch(error => console.error(error));

            function loadLetterChart(period) {
                fetch(dashboardBase + '/letter-chart?period=' + encodeURIComponent(period))
                    .then(response => {
                        if (!response.ok) throw new Error('Could not load the letter chart.');
                        return response.json();
                    })
                    .then(data => {
                        const chartData = {
                            labels: data.labels,
                            datasets: [
                                { label: 'All Letters', data: data.allLetters, backgroundColor: '#4680ff' },
                                { label: 'Replied', data: data.repliedLetters, backgroundColor: '#2ed8b6' },
                                { label: 'Not Replied', data: data.notRepliedLetters, backgroundColor: '#ffb64d' }
                            ]
                        };
                        if (letterActivityChart) {
                            letterActivityChart.data = chartData;
                            letterActivityChart.update();
                        } else {
                            letterActivityChart = new Chart(document.getElementById('letterActivityChart'), {
                                type: 'bar',
                                data: chartData,
                                options: {
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    scales: { y: { beginAtZero: true, ticks: { precision: 0 } } }
                                }
                            });
                        }
                    })
                    .catch(error => console.error(error));
            }

            loadLetterChart($('#letterChartPeriod').val());
            $('#letterChartPeriod').on('change', function () {
                loadLetterChart(this.value);
            });

            fetch('dashboard/new-emp')
                    .then(res => res.json())
                    .then(data => {
                        if (data && Array.isArray(data)) {
                            $('#newEmpWelcome').html('');
                            data.forEach(emp => {
                                const temp = '<div class="d-flex align-items-center mb-3">'
                                        + '<div class="bg-info text-white rounded-circle d-flex align-items-center justify-content-center" style="width:50px; height:50px;">'
                                        + '</div>'
                                        + '<div class="ml-3">'
                                        + '<strong>' + emp.name + '</strong><br>'
                                        + emp.designation + ' - ' + emp.department + '<br>'
                                        + '<small>Joined Date: ' + emp.entDate + '</small>'
                                        + '</div>'
                                        + '</div>'
                                $('#newEmpWelcome').append(temp);
                            });
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching new employees:', error);
                    });




            $('#savebtn').click(function () {
                if ($('#idea').val().trim() === '') {
                    Swal.fire("Empty Idea!", "Please Enter a Valid Idea!", "warning");
                    return;
                }
                if ($('#empNo').val().trim() === '') {
                    Swal.fire("Empty Employee No!", "Please Enter a Valid Employee No!", "warning");
                    return;
                }


                const formData = new FormData();
                formData.append('idea', $('#idea').val().trim());
                formData.append('empNo', $('#empNo').val().trim());
                Swal.fire({
                    title: 'Are you sure?',
                    text: "Your Idea Will be save !",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Yes, Continue!',
                    showLoaderOnConfirm: true,
                    searchHighlight: true,
                    preConfirm: () => {
                        return fetch('dashboard/save-idea', {
                            method: 'post',
                            body: formData
                        }).then(response => {
                            if (!response.ok) {
                                throw new Error(response.statusText);
                            }
                            return response.json();
                        }).catch(error => {
                            Swal.showValidationMessage('Request failed:' + error);
                        });
                    },
                    allowOutsideClick: () => !Swal.isLoading()

                }).then((result) => {
                    if (result.value) {
                        if (result.value.status !== 200) {
                            Swal.fire('Error!', result.value.msg, 'error');
                        } else {
                            Swal.fire('Successfull!', 'Your Idea has been updated.', 'success');
                            $('#formSection').find('input[type!=search]').val('')
                        }
                    }
                });
            });

        </script>
    </body>
</html>
