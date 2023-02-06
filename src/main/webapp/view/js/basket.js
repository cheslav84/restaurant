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


  if (localStorage.recordsPerPage == null) {
    localStorage.setItem('recordsPerPage', 4);
  } 

  if (localStorage.recordsPerPage) {
    $('select').val(localStorage.recordsPerPage);
  } 
 
});


$('select').on('change', function () {
  var currentVal = $(this).val();
  localStorage.setItem('recordsPerPage', currentVal);
});


$('.setPerPage').click(function () {
  const recordsPerPage = document.getElementById("recordsPerPage");
  this.href += '&recordsPerPage=' + recordsPerPage.value;
})


