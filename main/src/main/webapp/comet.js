
var atmosphereComet = (function() {

    var pollTimer;
    var http;
    var prevDataLength = 0;
    var nextLine = 0;

    var pageId = undefined;

    var isCometActive = false;

    return {

        createRequestObject: function() {
            var ro;
            if (window.XMLHttpRequest) {
                    ro = new XMLHttpRequest();
            } else {
                    ro = new ActiveXObject("Microsoft.XMLHTTP");
            }
            if (!ro)
                    debug("Couldn't start XMLHttpRequest object");
            return ro;
        },

        handleEvent: function(data) {
            console.log("Comet received: " + data);
        },

        handleResponse: function() {
            //console.log("handleResponse: readyState: " + http.readyState + " status: " + http.status)

            if (http.readyState != 4 && http.readyState != 3)
                return;
            if (http.readyState == 3 && http.status != 200)
                return;
            if (http.readyState == 4 && http.status != 200) {
                atmosphereComet.connect(pageId, true);
            }
            // In konqueror http.responseText is sometimes null here...
            if (http.responseText === null) {
                return;
            }

            while (prevDataLength != http.responseText.length) {
                if (http.readyState == 4  && prevDataLength == http.responseText.length)
                    break;

                prevDataLength = http.responseText.length;
                var response = http.responseText.substring(nextLine);

                var lines = response.split('\n');
                nextLine = nextLine + response.lastIndexOf('\n') + 1;
                if (response[response.length-1] != '\n')
                    lines.pop();


                for (var i = 0; i < lines.length; i++) {
                    var line = lines[i];
                    if (line) {
                        if (line.substring(0, 13) === "$$$MESSAGE$$$") {
                            var message = line.substring(13, line.length);
                            var json = JSON.parse(message);
                            atmosphereComet.handleEvent(json);
                        }
                    }
                }
            }

            if (http.readyState == 4 && prevDataLength == http.responseText.length) {
                atmosphereComet.connect(pageId, true);
            }
        },

        start: function() {
            console.log("Opening Comet connection")
            // reset
            prevDataLength = 0;
            nextLine = 0;

            http = atmosphereComet.createRequestObject();
            http.open('get', "/cometAtmosphereServlet");
            http.onreadystatechange = atmosphereComet.handleResponse;
            http.send(null);
            pollTimer = setInterval(atmosphereComet.handleResponse, 1 * 1000);
            isCometActive = true;
        },

        connect: function(force) {
            if (!isCometActive || force === true) {
                clearInterval(pollTimer);
                atmosphereComet.start();
            }
        }
    }

})();
