<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8"/>
  <meta name="viewport" content="width=1024"/>
  <meta name="apple-mobile-web-app-capable" content="yes"/>
  <title>HSC Sessions</title>

  <meta name="description"
        content="impress.js is a presentation tool based on the power of CSS3 transforms and transitions in modern browsers and inspired by the idea behind prezi.com."/>
  <meta name="author" content="Bartek Szopka"/>

  <!--<link href="http://fonts.googleapis.com/css?family=Open+Sans:regular,semibold,italic,italicsemibold|PT+Sans:400,700,400italic,700italic|PT+Serif:400,700,400italic,700italic" rel="stylesheet" />-->

  <link href="css/impress-demo.css" rel="stylesheet"/>

  <link rel="shortcut icon" href="favicon.png"/>
  <link rel="apple-touch-icon" href="apple-touch-icon.png"/>
  <script>
    var SESSION = "<%= request.getParameter("session")%>";
  </script>
</head>


<body class="impress-not-supported">

<!--
    For example this fallback message is only visible when there is `impress-not-supported` class on body.
-->
<div class="fallback-message">
  <p>Your browser <b>doesn't support the features required</b> by impress.js, so you are presented with a simplified
    version of this presentation.</p>

  <p>For the best experience please use the latest <b>Chrome</b>, <b>Safari</b> or <b>Firefox</b> browser.</p>
</div>


<div id="impress">

</div>


<script src="/js/impress.js"></script>
<script src='https://cdn.firebase.com/js/client/2.2.1/firebase.js'></script>
<!-- Google Endpoint client -->
<script src="/js/jquery.js"></script>
<script src="/js/hscsession_p.js"></script>
<script src="https://apis.google.com/js/client.js?onload=init"></script>


</body>
</html>
