###
Manages all channel objects in the channellist
###

class ChannelManager

    constructor: () ->
        @dom_elem = $ "#channels ul"
        @data = {}
        @dom = {}
    
    # sets the initial data array
    init: (@data) ->
        @callback_init
        
    update: (user) -> 
        
    
    migrate_all: (data) ->
        for id, value of @data
            migrate(id)
     
    # user info can be in: the group tab and in the stream
    # migrate updates all these entries if they exist, otherwise it creates them
    migrate:(id) ->
        value = @data[id]

  
