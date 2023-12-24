const overlay = document.getElementById('overlay');
const form = document.getElementById('add-form');
let isSubmitting = false;

function confirmSaveDocument() {
    if($('#documentTitle').val().length > 200){
        Swal.fire(
            'Title is too long!',
            'Please enter again.',
            'error'
        );
    } else if ($('#documentTitle').val() == '') {
        Swal.fire(
            'Title is empty!',
            'Please enter again.',
            'error'
        );
    } else if ($('#description').val().length > 500) {
        Swal.fire(
            'Description is too long!',
            'Please enter again.',
            'error'
        );
    } else {
        if(theEditor != null && file != null){
            if(theEditor.getData() == '' && file == null) {
                Swal.fire(
                    'No content!',
                    'Please create new content or upload available file.',
                    'error'
                );
            } else if(theEditor.getData() != '' && fileInput.value != ''){
                if(document.getElementById('editorWrapper').style.display != 'none'){
                    Swal.fire({
                        title: 'Display-content file is uploaded.',
                        html: `Do you want to save document with edited content?`,
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Yes'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            event.preventDefault(); // Ngăn chặn form submit mặc định

                            if (isSubmitting) {
                                return; // Nếu form đang được submit, không làm gì
                            }

                            isSubmitting = true; // Đánh dấu form đang được submit

                            // Hiển thị overlay
                            overlay.style.display = 'block';

                            // Xử lý logic submit form
                            form.submit().then(
                                function () {
                                    overlay.style.display = 'none';
                                    isSubmitting = false;
                                }
                            );
                        }
                    });
                } else {
                    Swal.fire({
                        title: 'Content is being edited',
                        html: `Do you want to save document with display content by file method?`,
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Yes'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            event.preventDefault(); // Ngăn chặn form submit mặc định

                            if (isSubmitting) {
                                return; // Nếu form đang được submit, không làm gì
                            }

                            isSubmitting = true; // Đánh dấu form đang được submit

                            // Hiển thị overlay
                            overlay.style.display = 'block';

                            // Xử lý logic submit form
                            form.submit().then(
                                function () {
                                    overlay.style.display = 'none';
                                    isSubmitting = false;
                                }
                            );
                        }
                    });
                }
            } else {
                Swal.fire({
                    title: 'Save document confirmation',
                    html: `Do you want to save?`,
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Yes'
                }).then((result) => {
                    if (result.isConfirmed) {
                        event.preventDefault(); // Ngăn chặn form submit mặc định

                        if (isSubmitting) {
                            return; // Nếu form đang được submit, không làm gì
                        }

                        isSubmitting = true; // Đánh dấu form đang được submit

                        // Hiển thị overlay
                        overlay.style.display = 'block';

                        // Xử lý logic submit form
                        form.submit().then(
                            function () {
                                overlay.style.display = 'none';
                                isSubmitting = false;
                            }
                        );
                    }
                });
            }
        } else {
            Swal.fire({
                title: 'Save document confirmation',
                html: `Do you want to save?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes'
            }).then((result) => {
                if (result.isConfirmed) {
                    event.preventDefault(); // Ngăn chặn form submit mặc định

                    if (isSubmitting) {
                        return; // Nếu form đang được submit, không làm gì
                    }

                    isSubmitting = true; // Đánh dấu form đang được submit

                    // Hiển thị overlay
                    overlay.style.display = 'block';

                    // Xử lý logic submit form
                    form.submit().then(
                        function () {
                            overlay.style.display = 'none';
                            isSubmitting = false;
                        }
                    );
                }
            });
        }
    }

}

