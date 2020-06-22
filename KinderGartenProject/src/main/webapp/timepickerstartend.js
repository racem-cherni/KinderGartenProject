$(document).ready(function() {
   $('.timepicker_start').timepicker({
       showLeadingZero: false,
       onSelect: tpStartSelect,
       maxTime: {
           hour: 16, minute: 30
       }
   });
   $('.timepicker_end').timepicker({
       showLeadingZero: false,
       onSelect: tpEndSelect,
       minTime: {
           hour: 9, minute: 15
       }
   });
});

// when start time change, update minimum for end timepicker
function tpStartSelect( time, endTimePickerInst ) {
   $('.timepicker_end').timepicker('option', {
       minTime: {
           hour: endTimePickerInst.hours,
           minute: endTimePickerInst.minutes
       }
   });
}

// when end time change, update maximum for start timepicker
function tpEndSelect( time, startTimePickerInst ) {
   $('.timepicker_start').timepicker('option', {
       maxTime: {
           hour: startTimePickerInst.hours,
           minute: startTimePickerInst.minutes
       }
   });
}