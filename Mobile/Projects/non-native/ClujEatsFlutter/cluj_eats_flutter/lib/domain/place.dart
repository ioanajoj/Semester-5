class Place {

  int _localId;
  int _remoteId;
  String _name;
  String _address;
  String _photo;

  Place(this._name, this._address, this._photo);

  Place.withId(this._localId, this._remoteId, this._name, this._address, this._photo);

  int get localId => _localId;

  int get remoteId => _remoteId;

  String get name => _name;

  String get address => _address;

  String get photo => _photo;

  set localId(int newLocalId) {
    this._localId = newLocalId;
  }

  set remoteId(int newRemoteId) {
    this._remoteId = newRemoteId;
  }

  set name(String newName) {
    if(newName.length <= 50) {
      this._name = newName;
    }
  }

  set address(String newAddress) {
    this._address = newAddress;
  }

  set photo(String newPhoto) {
    this._photo = newPhoto;
  }

  Map<String, dynamic> toMap() {
    var map = Map<String, dynamic>();
    if (_localId != null) {
      map['localId'] = _localId.toString();
    }
    if (_remoteId != null) {
      map['remoteId'] = _remoteId.toString();
    }
    map['name'] = _name;
    map['address'] = _address;
    map['photo'] = _photo;
    return map;
  }

  Map<String, dynamic> toJson() {
    var map = Map<String, dynamic>();
    map['name'] = _name;
    map['address'] = _address;
    map['photo'] = _photo;
    return map;
  }

  Place.fromMapObject(Map<String, dynamic> map) {
    this._localId = null;
    this._remoteId = map['id'];
    this._name = map['name'];
    this._address = map['address'];
    this._photo = map['photo'];
  }

  Place.fromDB(Map<String, dynamic> map) {
    this._localId = map['localId'];
    this._remoteId = map['remoteId'];
    this._name = map['name'];
    this._address = map['address'];
    this._photo = map['photo'];
  }

}