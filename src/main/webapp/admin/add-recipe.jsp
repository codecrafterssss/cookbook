<!DOCTYPE html>
<html>
<head>
<title>Add Recipe</title>
</head>
<body>

<h2>ADD NEW RECIPE</h2>

<form action="../admin/recipes" method="post">

    <input type="hidden" name="action" value="add">

    Title: <br>
    <input type="text" name="title" required><br><br>

    Instructions: <br>
    <textarea name="instructions" rows="6" cols="50" required></textarea><br><br>

    Cook Time (minutes): <br>
    <input type="number" name="cookTime" required><br><br>

    Total Calories: <br>
    <input type="number" name="totalCalories" required><br><br>

    Chef ID (enter existing chef_id): <br>
    <input type="number" name="chefId" required><br><br>

    <button type="submit">Add Recipe</button>
</form>

<br>
<a href="view-recipes.jsp">Back</a>

</body>
</html>
