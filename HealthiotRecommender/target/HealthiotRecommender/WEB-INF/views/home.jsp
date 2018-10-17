<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Activity Recommender</title>
</head>
<body>
<h1>
	The Activity Recommender    
</h1>
<P>  The target of The Activity Recommender is to recommend activity plans for Insomnia patients.
Insomnia is a sleep disorder that is characterized by difficulty falling and/or staying asleep. 
These people can not have a good quality sleep easily. 
Therefore, we recommend them a physical activity plan which can help to improve their sleep quality.
</P>

<P>  To recommend activity plans, The Activity Recommender is using a collaborative filtering based on a real-world dataset.
The dataset contains sleep qualities and steps statistics of users.
</P>
<P>  Current number of users in the dataset: ${users}. </P>
<P>  Current number of items in the dataset: ${items}. </P>


</body>
</html>
