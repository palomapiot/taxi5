from flask import Flask, request, jsonify, make_response
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
from flask_login import LoginManager, current_user, login_user, UserMixin, logout_user
#from flask_httpauth import HTTPBasicAuth
#auth = HTTPBasicAuth()
from werkzeug.security import generate_password_hash, check_password_hash
import os

app = Flask(__name__)
app.secret_key='taxi5'
# Login
login = LoginManager(app)
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'crud.sqlite')
db = SQLAlchemy(app)
ma = Marshmallow(app)

@login.user_loader
def load_user(id):
    return User.query.get(int(id))

##############
##  Models  ##
##############

class User(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    firstname = db.Column(db.String(80))
    lastname = db.Column(db.String(80))
    email = db.Column(db.String(120), unique=True)
    phone = db.Column(db.String(9), unique=True)
    psswd = db.Column(db.String(128))
    rides = db.relationship('Ride', backref='user', lazy=True)

    def set_password(self, password):
        self.psswd = generate_password_hash(password)

    def check_password(self, password):
        return check_password_hash(self.psswd, password)

    def __init__(self, firstname, lastname, email, phone, psswd):
        self.firstname = firstname
	self.lastname = lastname
        self.email = email
	self.phone = phone
	self.set_password(psswd)


class UserSchema(ma.Schema):
    class Meta:
        # Fields to expose
        fields = ('id', 'firstname', 'lastname', 'email', 'phone', 'psswd')


user_schema = UserSchema()
users_schema = UserSchema(many=True)


class Ride(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    origin = db.Column(db.String(240))
    destination = db.Column(db.String(240))
    ridedate = db.Column(db.BigInteger)
    price = db.Column(db.Float)
    userid = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)

    def __init__(self, origin, destination, ridedate, price, userid):
        self.origin = origin
	self.destination = destination
        self.ridedate = ridedate
	self.price = price
	self.userid = userid


class RideSchema(ma.Schema):
    class Meta:
        # Fields to expose
        fields = ('id', 'origin', 'destination', 'ridedate', 'price', 'userid')


ride_schema = RideSchema()
rides_schema = RideSchema(many=True)


##############
##  ROUTES  ##
##############

@app.route('/')
def hello():
    return "Holi mundi"

# endpoint to login user
@app.route('/login', methods=['GET', 'POST'])
def login():
    if current_user.is_authenticated:
        return user_schema.jsonify(current_user)
    user = User.query.filter_by(email=request.json['email']).first()
    if user is None or not user.check_password(request.json['psswd']):
        return not_found('Invalid username or password')
    login_user(user, remember=True)
    return user_schema.jsonify(user)

# endpoint to logout user
@app.route('/logout')
def logout():
    logout_user()
    return jsonify(success=True)

# endpoint to create new user
@app.route("/user", methods=["POST"])
def add_user():
    firstname = request.json['firstname']
    lastname = request.json['lastname']
    email = request.json['email']
    phone = request.json['phone']
    psswd = request.json['psswd']
    
    new_user = User(firstname, lastname, email, phone, psswd)

    db.session.add(new_user)
    db.session.commit()

    return user_schema.jsonify(new_user)


# endpoint to show all users
@app.route("/user", methods=["GET"])
def get_user():
    all_users = User.query.all()
    result = users_schema.dump(all_users)
    return jsonify(result.data)


# endpoint to get user detail by id
@app.route("/user/<id>", methods=["GET"])
def user_detail(id):
    user = User.query.get(id)
    return user_schema.jsonify(user)

# endpoint to get current user detail
@app.route("/currentuser", methods=["GET"])
#@auth.login_required
def get_current_user():
    user = current_user
    return user_schema.jsonify(user)


# endpoint to get user rides by user id
@app.route("/rides/<id>", methods=["GET"])
def get_user_rides(id):
    rides = Ride.query.filter(Ride.userid == id)
    #user.rides
    return rides_schema.jsonify(rides)


# endpoint to update user
@app.route("/user/<id>", methods=["PUT"])
def user_update(id):
    user = User.query.get(id)
    firstname = request.json['firstname']
    lastname = request.json['lastname']
    email = request.json['email']
    phone = request.json['phone']

    user.firstname = firstname
    user.lastname = lastname
    user.email = email
    user.phone = phone

    db.session.commit()
    return user_schema.jsonify(user)

# endpoint to update user psswd
@app.route("/userpsswd/<id>", methods=["PUT"])
def user_update_psswd(id):
    user = User.query.get(id)
    psswd = request.json['psswd']

    user.set_password(psswd)

    db.session.commit()
    return user_schema.jsonify(user)


# endpoint to delete user
@app.route("/user/<id>", methods=["DELETE"])
def user_delete(id):
    user = User.query.get(id)
    db.session.delete(user)
    db.session.commit()

    return user_schema.jsonify(user)

###########
##  RIDE ##
###########

# endpoint to create new ride
@app.route("/ride", methods=["POST"])
def add_ride():
    origin = request.json['origin']
    destination = request.json['destination']
    ridedate = request.json['ridedate']
    price = None
    userid = request.json['userid']
    
    new_ride = Ride(origin, destination, ridedate, price, userid)

    db.session.add(new_ride)
    db.session.commit()

    return ride_schema.jsonify(new_ride)

# endpoint to update ride (set price)
@app.route("/ride/<id>", methods=["PUT"])
def ride_update(id):
    ride = Ride.query.get(id)
    price = request.json['price']

    ride.price = price

    db.session.commit()
    return ride_schema.jsonify(ride)

@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
