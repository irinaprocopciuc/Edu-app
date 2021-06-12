from flask import Flask, request, session
from flask_socketio import SocketIO, emit , join_room, leave_room


app = Flask(__name__)
socketio = SocketIO(app, cors_allowed_origins='http://localhost:4200')

clients = list()
ids = list()


@app.route('/')
def index():
    return app.send_static_file('')


@socketio.on('connected')
def connected(userId):
    clients.append(request.sid)
    ids.append(userId)

@socketio.on('disconnect')
def disconnect():
    ids.remove(ids[clients.index(request.sid)])
    clients.remove(request.sid)
    response = dict()
    for i in ids:
        response[i] = clients[ids.index(i)]
    socketio.emit('receive_users', response)
    print('disconnect')


@socketio.on('get_users')
def get_users():
    response = dict()
    for i in ids:
        response[i] = clients[ids.index(i)]
    socketio.emit('receive_users', response)

@socketio.on('send_message')
def send_message(sender,msg, date, receiver):
    if receiver in ids:
        response = dict()
        response['from']= sender.replace('\"', '')
        response['msg'] = msg.replace('\"', '')
        response['date'] = date
        print(response['msg'])
        socketio.emit('receive_message', response, room=clients[ids.index(receiver)])


if __name__ == '__main__':
    socketio.run(app)
