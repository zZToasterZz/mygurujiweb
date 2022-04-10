$('#getSchedule').on('click', function(){
    var batches = $('#selectedBatches').val();
    var batchesPayload = '['+batches+']';
    getData(batchesPayload);
});

function getData(batchesPayload){
    $.ajax({
        url: "/manageassessschedule/examSchedulesByBatches",
        type: "POST",
        dataType: "JSON",
        data: {"batches": batchesPayload},
        success: function(result){
            results =  result;

            var datas = [];

            for(res of results) {
                var evt = new Object();
                evt.title = res.assessmentitle;
                evt.start = res.startdatetime;
                evt.end = res.enddatetime;
                datas.push(evt);
            }
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                height: 'auto',
                stickyHeaderDates: false,
                headerToolbar: {
                    left: 'prev,next',
                    center: 'title',
                    right: 'listDay,listWeek'
                },

                // customize the button names,
                // otherwise they'd all just say "list"
                views: {
                    listDay: { buttonText: 'day' },
                    listWeek: { buttonText: 'week' }
                },

                initialView: 'listWeek',
                displayEventTime: true,
                displayEventEnd: true,
                //initialDate: '2020-06-12',
                navLinks: true, // can click day/week names to navigate views
                editable: true,
                dayMaxEvents: true, // allow "more" link when too many events
                events: datas
            });

            calendar.render();
        },
        error: function(result){
            alert("ERROR : "+JSON.stringify(result));
        }
    })
}