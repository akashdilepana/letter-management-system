<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>RDA - LMS</title>
        <%@include file="./JSPF/header.jsp" %>
        <link rel="stylesheet" type="text/css" href="files/css/dataTables.searchHighlight.css">
    </head>
    <body>
        <%@include file="./JSPF/loader.jsp" %>
        <div id="pcoded" class="pcoded">
            <div class="pcoded-overlay-box"></div>
            <div class="pcoded-container navbar-wrapper">
                <%@include file="./JSPF/navbar.jsp" %>
                <div class="pcoded-main-container">
                    <div class="pcoded-wrapper">
                        <%@include file="./JSPF/sidenav.jsp" %>
                        <div class="pcoded-content">
                            <div class="pcoded-inner-content">
                                <div class="main-body">
                                    <div class="page-wrapper">
                                        <div class="page-body">

                                            <div class="row" id="tableSection">
                                                <div class="col-lg-10 offset-lg-1 col-xl-8 offset-xl-2 col-12">
                                                    <div class="card">
                                                        <div class="card-header">
                                                            <h6>Letters</h6>
                                                            <div class="card-header-right">
                                                                <ul class="list-unstyled card-option">
                                                                    <li><i class="feather icon-maximize full-card"></i></li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <div class="card-block p-b-0">
                                                            <div class="table-responsive">
                                                                <table class="table table-hover table-bordered m-b-0" id="tbll">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>S/N</th>
                                                                            <th>Received Date</th>
                                                                            <th>Letter My No</th>
                                                                            <th>Address TO</th>
                                                                            <th>Subject</th>
                                                                            <th>Sent By</th>
                                                                            <th>Reply</th>
                                                                            <th style="width:1px;">Action</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>

                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                        <div class="card-footer">
                                                            <div class="text-right">
                                                                <button id="addLetterBtn" class="btn btn-sm waves-effect waves-light btn-danger"><i class="icon feather icon-plus"></i>Add Letter</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row" id="formSection" style="display: none">
                                                <div class="col-lg-8 offset-lg-2 col-12">
                                                    <div class="card">
                                                        <div class="card-header">
                                                            <h6>Save Letter</h6>
                                                            <div class="card-header-right">
                                                                <ul class="list-unstyled card-option">
                                                                    <li><i class="feather icon-x cls-card"></i></li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <div class="card-block p-b-0">
                                                            <form id="letterForm" enctype="multipart/form-data">
                                                            <div class="row">
                                                                <div class="col-12 col-lg-12">
                                                                    <div class="row">

                                                                        <div class="col-12">
                                                                            <p class="text-muted">S/N is generated automatically when this letter is saved. Received date is recorded automatically.</p>
                                                                        </div>

                                                                        <div class="col-lg-6 col-12">
                                                                            <div class="form-group">
                                                                                <label for="addressTo">Address To<span class="text-danger">*</span></label>
                                                                                <select id="addressTo" class=""></select>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-6 col-12">
                                                                            <div class="form-group">
                                                                                <label for="sentBy">Sent By<span class="text-danger">*</span></label>
                                                                                <select id="sentBy" class=""></select>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-6 col-12">
                                                                            <div class="form-group">
                                                                                <label for="subject">Subject<span class="text-danger">*</span></label>
                                                                                <input id="subject" type="text" name="subject" class="form-control"  required autocomplete="off">
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-6 col-12">
                                                                            <div class="form-group">
                                                                                <label for="letterMyNo">Letter My No<span class="text-danger">*</span></label>
                                                                                <input id="letterMyNo" type="text" class="form-control" required maxlength="100" autocomplete="off">
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-6 col-12">
                                                                            <div class="form-group">
                                                                                <label for="documentDate">Letter Date<span class="text-danger">*</span></label>
                                                                                <input id="documentDate" type="date" class="form-control" required>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-6 col-12">
                                                                            <div class="form-group">
                                                                                <label for="actionTakenBy">Action Taken By</label>
                                                                                <select id="actionTakenBy" class="" multiple></select>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-6 col-12">
                                                                            <div class="form-group">
                                                                                <label for="replyRequired">Reply Required?</label>
                                                                                <select id="replyRequired" class="form-control">
                                                                                    <option value="false">No</option>
                                                                                    <option value="true">Yes</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                        <div id="replyFields" class="row col-12" style="display:none">
                                                                            <div class="col-lg-6 col-12">
                                                                                <div class="form-group">
                                                                                    <label for="replyLetterMyNo">Reply Letter My No<span class="text-danger">*</span></label>
                                                                                    <input id="replyLetterMyNo" type="text" class="form-control" maxlength="100">
                                                                                </div>
                                                                            </div>
                                                                            <div class="col-lg-6 col-12">
                                                                                <div class="form-group">
                                                                                    <label for="replyDate">Reply Letter Date<span class="text-danger">*</span></label>
                                                                                    <input id="replyDate" type="date" class="form-control">
                                                                                </div>
                                                                            </div>
                                                                            <div class="col-12">
                                                                                <div class="form-group">
                                                                                    <label for="replyFile">Upload Reply Letter<span class="text-danger">*</span></label>
                                                                                    <input id="replyFile" type="file" class="form-control" accept=".pdf,.doc,.docx,.jpg,.jpeg,.png">
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-12">
                                                                            <div class="form-group">
                                                                                <label for="letterFile">Upload Letter<span class="text-danger">*</span></label>
                                                                                <input id="letterFile" type="file" class="form-control" accept=".pdf,.doc,.docx,.jpg,.jpeg,.png" required>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            </form>
                                                        </div>
                                                        <div class="card-footer">
                                                            <div class="text-right">
                                                                <button id="saveBtn" class="btn btn-sm waves-effect waves-light btn-primary"><i class="icon feather icon-save"></i>Save</button>
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
            </div>
        </div>

        <%@include file="./JSPF/scripts.jsp" %>
        <script type="text/javascript" src="files/js/slimselect.js"></script>
        <script type="text/javascript" src="files/js/jquery.highlight.js"></script>
        <script type="text/javascript" src="files/js/datatables.min.js"></script>
        <script type="text/javascript" src="files/js/sweetalert2.js"></script>
        <script type="text/javascript" src="files/js/func.js"></script>
        <script type="text/javascript" src="files/js/autoNumeric.js"></script>
        <script type="text/javascript" src="files/js/dataTables.responsive.min.js"></script>
        <script type="text/javascript" src="files/js/dataTables.searchHighlight.min.js"></script>
        <script>
            $.fn.dataTable.ext.errMode = 'none';

            const table = $('#tbll').DataTable({
                aLengthMenu: [[5, 10, 25, -1], [5, 10, 25, 'All']],
                pageLength: 10,
                processing: true,
                serverSide: true,
                order: [[0, 'desc']],
                ajax: {
                    url: 'letter/letters',
                    contentType: 'application/json',
                    type: 'POST',
                    data: d => JSON.stringify(d),
                    error: xhr => console.error('Unable to load letters', xhr)
                },
                columns: [
                    { data: 'sno' },
                    { data: 'date' },
                    { data: 'letterMyNo' },
                    { data: 'addressTo' },
                    { data: 'subject' },
                    { data: 'sentBy' },
                    { data: 'reply' },
                    {
                        data: null,
                        orderable: false,
                        searchable: false,
                        render: function (data, type, row) {
                            return row.reply === 'Yes' && row.replyStatus === 'Not Replied'
                                ? '<button type="button" class="btn btn-sm btn-primary saveReply" data-id="' + row.id + '">Save Reply</button>'
                                : '';
                        }
                    }
                ],
                createdRow: row => $(row).css('cursor', 'pointer')
            });

            function makeUserSelect(selector, placeholder, multiple) {
                const select = new SlimSelect({
                    select: selector,
                    placeholder,
                    searchHighlight: true,
                    closeOnSelect: !multiple,
                    allowDeselect: !multiple,
                    ajax: function (search, callback) {
                        fetch('letter/search-letter-users', {
                            method: 'POST',
                            body: new URLSearchParams({ search: search || '' })
                        }).then(response => response.json()).then(callback)
                          .catch(() => callback([]));
                    }
                });
                return select;
            }

            const addressToSelect = makeUserSelect('#addressTo', 'Select recipient', false);
            const sentBySelect = makeUserSelect('#sentBy', 'Select sender', false);

            function divisionSelect(selector, placeholder, multiple) {
                const select = new SlimSelect({
                    select: selector,
                    placeholder,
                    searchHighlight: true,
                    closeOnSelect: !multiple,
                    allowDeselect: !multiple,
                    ajax: function (search, callback) {
                        fetch('letter/search-division-users', {
                            method: 'POST',
                            body: new URLSearchParams({ search: search || '' })
                        }).then(response => response.json()).then(callback)
                          .catch(() => callback([]));
                    }
                });
                return select;
            }
            const actionTakenBySelect = divisionSelect('#actionTakenBy', 'Select action owners', true);

            $('#addLetterBtn').on('click', function () {
                $('#letterForm')[0].reset();
                addressToSelect.set('');
                sentBySelect.set('');
                actionTakenBySelect.set([]);
                $('#replyFields').hide();
                $('#replyLetterMyNo, #replyDate, #replyFile').prop('required', false);
                const today = new Date();
                $('#documentDate').val(today.getFullYear() + '-' + String(today.getMonth() + 1).padStart(2, '0') + '-' + String(today.getDate()).padStart(2, '0'));
                $('#tableSection').hide();
                $('#formSection').fadeIn();
            });

            $('.cls-card').on('click', function () {
                $('#formSection').hide();
                $('#tableSection').fadeIn();
            });

            $('#replyRequired').on('change', function () {
                const needsReply = this.value === 'true';
                $('#replyFields').toggle(needsReply);
                $('#replyLetterMyNo, #replyDate, #replyFile').prop('required', needsReply);
            });

            $('#saveBtn').on('click', function () {
                const form = $('#letterForm')[0];
                if (!form.reportValidity()) return;
                if (!addressToSelect.selected() || !sentBySelect.selected()) {
                    Swal.fire('Required fields', 'Choose both Address To and Sent By.', 'warning');
                    return;
                }

                const formData = new FormData();
                formData.append('addressTo', addressToSelect.selected());
                formData.append('sentBy', sentBySelect.selected());
                formData.append('subject', $('#subject').val().trim());
                formData.append('letterMyNo', $('#letterMyNo').val().trim());
                formData.append('documentDate', $('#documentDate').val());
                (actionTakenBySelect.selected() || []).forEach(id => formData.append('actionTakenBy', id));
                formData.append('replyRequired', $('#replyRequired').val());
                formData.append('letterFile', $('#letterFile')[0].files[0]);
                if ($('#replyRequired').val() === 'true') {
                    formData.append('replyLetterMyNo', $('#replyLetterMyNo').val().trim());
                    formData.append('replyDate', $('#replyDate').val());
                    formData.append('replyFile', $('#replyFile')[0].files[0]);
                }

                Swal.fire({
                    title: 'Save this letter?',
                    text: "Designation Will be saved and you won't be able to revert this!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Yes, Continue!',
                    showLoaderOnConfirm: true,
                    searchHighlight: true,
                    preConfirm: () => fetch('letter/save-letter', { method: 'POST', body: formData })
                        .then(async response => {
                            const result = await response.json();
                            if (!response.ok) throw new Error(result.message || 'Could not save the letter.');
                            return result;
                        })
                        .catch(error => Swal.showValidationMessage(error.message)),
                    allowOutsideClick: () => !Swal.isLoading()
                }).then(result => {
                    if (result.isConfirmed && result.value && result.value.status === 200) {
                        Swal.fire('Saved', result.value.message, 'success');
                        table.ajax.reload();
                        $('#formSection').hide();
                        $('#tableSection').fadeIn();
                    }
                });
            });

            $('#tbll').on('click', '.saveReply', function () {
                const letterId = $(this).data('id');
                Swal.fire({
                    title: 'Save Reply Letter',
                    html: '<input id="swalReplyNo" class="swal2-input" placeholder="Reply Letter My No" required>' +
                          '<label for="swalReplyDate">Reply Letter Date</label>' +
                          '<input id="swalReplyDate" type="date" class="swal2-input">' +
                          '<label for="swalReplyFile">Reply Letter File</label>' +
                          '<input id="swalReplyFile" type="file" class="swal2-file" accept=".pdf,.doc,.docx,.jpg,.jpeg,.png" required>',
                    showCancelButton: true,
                    confirmButtonText: 'Save Reply',
                    showLoaderOnConfirm: true,
                    preConfirm: () => {
                        const popup = Swal.getPopup();
                        const replyNo = popup.querySelector('#swalReplyNo').value.trim();
                        const replyFile = popup.querySelector('#swalReplyFile').files[0];
                        if (!replyNo || !replyFile) {
                            Swal.showValidationMessage('Enter the reply number and select the reply file.');
                            return false;
                        }
                        const formData = new FormData();
                        formData.append('replyLetterMyNo', replyNo);
                        formData.append('replyDate', popup.querySelector('#swalReplyDate').value);
                        formData.append('replyFile', replyFile);
                        return fetch('letter/letters/' + letterId + '/reply', { method: 'POST', body: formData })
                            .then(async response => {
                                const result = await response.json();
                                if (!response.ok) throw new Error(result.message || 'Could not save reply letter.');
                                return result;
                            })
                            .catch(error => Swal.showValidationMessage(error.message));
                    },
                    allowOutsideClick: () => !Swal.isLoading()
                }).then(result => {
                    if (result.isConfirmed && result.value && result.value.status === 200) {
                        Swal.fire('Saved', result.value.message, 'success');
                        table.ajax.reload();
                    }
                });
            });

            function escapeHtml(value) {
                return String(value == null ? '' : value).replace(/[&<>"']/g, function (character) {
                    return {'&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'}[character];
                });
            }

            $('#tbll tbody').on('click', 'tr', function (event) {
                if ($(event.target).closest('button, a').length) return;
                const row = table.row(this).data();
                if (!row) return;
                fetch('letter/details/' + row.id)
                    .then(response => {
                        if (!response.ok) throw new Error('Could not load letter details.');
                        return response.json();
                    })
                    .then(details => {
                        const actionOwners = (details.actionTakenBy || []).map(escapeHtml).join(', ') || '—';
                        const replyFileLink = details.replyFileUrl
                            ? '<a target="_blank" rel="noopener" href="' + escapeHtml(details.replyFileUrl) + '">Open reply letter</a>'
                            : '—';
                        const body = '<div class="text-left">' +
                            '<p><b>S/N:</b> ' + escapeHtml(details.sno) + '</p>' +
                            '<p><b>Received date:</b> ' + escapeHtml(details.receivedDate) + '</p>' +
                            '<p><b>Letter date:</b> ' + escapeHtml(details.documentDate) + '</p>' +
                            '<p><b>Letter My No:</b> ' + escapeHtml(details.letterMyNo) + '</p>' +
                            '<p><b>Address To:</b> ' + escapeHtml(details.addressTo) + '</p>' +
                            '<p><b>Sent By:</b> ' + escapeHtml(details.sentBy) + '</p>' +
                            '<p><b>Subject:</b> ' + escapeHtml(details.subject) + '</p>' +
                            '<p><b>Action Taken By:</b> ' + actionOwners + '</p>' +
                            '<p><b>Reply Required:</b> ' + (details.replyRequired ? 'Yes' : 'No') + '</p>' +
                            '<p><b>Reply Status:</b> ' + (details.replyFileUrl ? 'Replied' : 'Not Replied') + '</p>' +
                            '<p><b>Reply Letter My No:</b> ' + escapeHtml(details.replyLetterMyNo || '—') + '</p>' +
                            '<p><b>Reply date:</b> ' + escapeHtml(details.replyDate || '—') + '</p>' +
                            '<p><b>Status:</b> ' + escapeHtml(details.status) + '</p>' +
                            '<p><b>Letter file:</b> <a target="_blank" rel="noopener" href="' + escapeHtml(details.letterFileUrl) + '">Open letter</a></p>' +
                            '<p><b>Reply file:</b> ' + replyFileLink + '</p></div>';
                        Swal.fire({title: 'Letter Details', html: body, width: 700});
                    })
                    .catch(error => Swal.fire('Error', error.message, 'error'));
            });

            if (new URLSearchParams(window.location.search).get('new') === '1') {
                $('#addLetterBtn').trigger('click');
            }
        </script>
    </body>
</html>
