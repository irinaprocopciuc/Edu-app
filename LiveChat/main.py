from flask import Flask, request
from flask_socketio import SocketIO, emit


app = Flask(__name__)
socketio = SocketIO(app, cors_allowed_origins='http://localhost:4200')

clients = list()
ids = list()


@app.route('/')
def index():
    return app.send_static_file('')


@socketio.on('connected')
def connected(userId):
    print(request.sid)
    clients.append(request.sid)
    ids.append(userId)

@socketio.on('disconnect')
def disconnect():
    print(clients)
    print(request.sid)
    ids.remove(ids[clients.index(request.sid)])
    clients.remove(request.sid)
    print('disconnect')


@socketio.on('message')
def abcd(msg,to):
    if to in ids:
        #send to client
        emit('msg',msg,room = clients[ids.index[to]])
    else:
        print(msg)

@socketio.on('getOnlineUsers')
def abcd(msg,to):
    return ids


if __name__ == '__main__':
    socketio.run(app)
