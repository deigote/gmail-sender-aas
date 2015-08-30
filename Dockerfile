FROM java

ENV TERM xterm

RUN \
        apt-get update -qq && \
        apt-get install git

RUN \
        cd / && \
        git clone https://github.com/deigote/gmail-sender-aas.git && \
        echo '#!/bin/bash' > /run.sh && \
	echo 'cd /gmail-sender-aas ; git pull ; ./gradlew run' >> /run.sh && \
        chmod u+x /run.sh

CMD ["/run.sh"]
