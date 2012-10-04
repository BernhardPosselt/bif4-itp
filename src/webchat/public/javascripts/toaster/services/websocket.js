(function() {
  var _this = this;

  __t('services').WebChatWebSocket = (function() {

    function WebChatWebSocket() {
      this._callbacks = {
        onOpen: function() {
          return console.info('websocket is open');
        },
        onReceive: function() {},
        onError: function() {
          return console.error("websocket error occured");
        },
        onClose: function() {
          return console.info("closed websocket");
        }
      };
    }

    WebChatWebSocket.prototype.connect = function(domain, path, ssl) {
      var Socket, protocol, url,
        _this = this;
      if (domain == null) {
        domain = document.location.host;
      }
      if (path == null) {
        path = "/websocket";
      }
      this.ssl = ssl != null ? ssl : false;
      Socket = window['MozWebSocket'] || window['WebSocket'];
      if (this.ssl) {
        protocol = "wss://";
      } else {
        protocol = "ws://";
      }
      url = "" + protocol + domain + path;
      try {
        this._connection = new Socket(url);
        this._connection.onopen = function() {
          _this._callbacks.onOpen();
          return window.onbeforeunload = function() {
            return _this.close();
          };
        };
        this._connection.onmessage = function(event) {
          var json, msg;
          msg = event.data;
          json = JSON.parse(msg);
          _this._callbacks.onReceive(json);
          console.info("Received: " + msg);
          if (json.type === 'status') {
            if (json.data.level !== 'ok') {
              return console.warn(json.data.msg);
            }
          }
        };
        this._connection.onclose = function() {
          return _this._callbacks.onClose();
        };
        return this._connection.onerror = function() {
          return _this._callbacks.onError();
        };
      } catch (error) {
        return console.error("Cant connect to " + url);
      }
    };

    WebChatWebSocket.prototype.onOpen = function(callback) {
      return this._callbacks.onOpen = callback;
    };

    WebChatWebSocket.prototype.onReceive = function(callback) {
      return this._callbacks.onReceive = callback;
    };

    WebChatWebSocket.prototype.onClose = function(callback) {
      return this._callbacks.onClose = callback;
    };

    WebChatWebSocket.prototype.onError = function(callback) {
      return this._callbacks.onError = callback;
    };

    WebChatWebSocket.prototype.send = function(msg) {
      this._connection.send(msg);
      return console.info("Sending " + msg);
    };

    WebChatWebSocket.prototype.sendJSON = function(json_object) {
      var msg;
      msg = JSON.stringify(json_object);
      return this.send(msg);
    };

    return WebChatWebSocket;

  })();

  angular.module('WebChat').factory('websocket', [
    '$rootScope', 'WEBSOCKET_DOMAIN', 'WEBSOCKET_PATH', 'WEBSOCKET_SSL', function($rootScope, WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL) {
      var socket;
      socket = new WebChatWebSocket();
      socket.connect(WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL);
      socket.onReceive(function(message) {
        return $rootScope.$broadcast('message', message);
      });
      return socket;
    }
  ]);

}).call(this);
