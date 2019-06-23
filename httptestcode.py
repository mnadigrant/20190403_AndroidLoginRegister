#!flask/bin/python
from flask import abort
from flask import Flask, jsonify
from flask import make_response
from flask import request



users = [
	{
	'userID':1,
	'userName': u'MNADI GRANT',
	'userEmail':u'mnadi@gmail.com',
	'hashedPassword': u'mnadi'
	},
	{
	'userID':2,
	'userName': u'GEORGETTE',
	'userEmail':u'gee@gmail.com',
	'hashedPassword': u'gee'
	}
]
tasks = [
	{
	'id':1,
	'title': u'Buy groceries',
	'description':u'Milk,Cheese,Stuff',
	'done': False
	},
	{
	'id':2,
	'title': u'Study Chinese',
	'description': u'Learn All new Characters',
	'done': False
	}
	]

@app.errorhandler(404)
def not_found(error):
	return make_response(jsonify({'error': 'Not found'}),404)

#users
@app.route('/todo/api/v1.0/users', methods=['GET'])
def get_users():
	return jsonify(users)

#==================================CODE USED TO REQUEST CHECK USERNAME AND PASSWORD================================
@app.route('/todo/api/v1.0/users/<string:user_name> && <string:user_Password>', methods=['GET'])
def get_user(user_name,user_Password):
	user = [user for user in users if user['hashedPassword'] == user_Password and user['userName'] == user_name]
	if len(user) == 0:
		abort(404)
	return jsonify({'message': u'User Login Successful'})

#==================================CODE USED TO RETRIEVE ALL THE TASKS AVAILABLE===================================
@app.route('/todo/api/v1.0/tasks', methods=['GET'])
def get_tasks():
	return jsonify({'task': task[0]})



@app.route('/todo/api/v1.0/tasks/<int:task_id>', methods=['GET'])
def get_task(task_id):
	task = [task for task in tasks if task['id'] == task_id]
	if len(task) == 0:
		abort(404)
	return jsonify({'task': task[0]})

@app.route('/todo/api/v1.0/users', methods = ['POST'])
def create_user():
	if not request.json or not 'userName' in request.json:
		abort(400)
	user = {
		'userID': users[-1]['userID'] + 1,
		'userName': request.json['userName'],
		'userEmail': request.json.get('userEmail',""),
		'hashedPassword': request.json.get('hashedPassword',"")
		}
	users.append(user)
	return jsonify({'user': user}),201


@app.route('/todo/api/v1.0/tasks', methods = ['POST'])
def create_task():
	if not request.json or not 'title' in request.json:
		abort(400)
	task = {
		'id': tasks[-1]['id'] + 1,
		'title': request.json['title'],
		'description': request.json.get('description',""),
		'done': False
		}
	tasks.append(task)
	return jsonify({'task': task}),201



@app.route('/todo/api/v1.0/tasks/<int:task_id>', methods =['DELETE'])
def delete_task(task_id):
	task = [task for task in tasks if task['id'] == task_id]
	if len(task) == 0:
		abort(404)
	tasks.remove(task[0])
	return jsonify({'result': True})
	


if __name__ == '__main__':
	app.run(host = '0.0.0.0',debug=True, port='5000')
	
		
