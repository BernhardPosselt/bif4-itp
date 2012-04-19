###
This class handles the reloading of channels and messages
###

class Manager

    constructor: (@interval) ->
        @users = new UserManager()
        @channels = new ChannelManager()

    


