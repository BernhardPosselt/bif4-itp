class FileManager

    constructor: (@callback_init) ->
        @dom_elem = $ "#channel_sidebar #groups"
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
