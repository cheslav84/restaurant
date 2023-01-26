$(document).ready(function () {
  const classesNodeList = document.querySelectorAll('.menu-page-section');
  var classes = Array.prototype.slice.call(classesNodeList);

  for (let i = 0; i <= classes.length; i++) {
    if (i % 2 === 0) {
      $(classes[i]).addClass('white-background');
    }
  }

  const last = Array.from(
    document.querySelectorAll('#delimiter')
  ).pop();

  $(last).addClass('last-padding');

  if (localStorage.selectVal) {
    $('select').val(localStorage.selectVal);
  } 
 
});


$('select').on('change', function () {
  var currentVal = $(this).val();
  localStorage.setItem('selectVal', currentVal);
});


$('.setPerPage').click(function () {
  const recordsPerPage = document.getElementById("recordsPerPage");
  this.href += '&recordsPerPage=' + recordsPerPage.value;
})


