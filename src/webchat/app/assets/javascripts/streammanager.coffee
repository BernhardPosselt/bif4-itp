class StreamManager

    constructor: (@callback_init) ->
        @dom_elem = $ "#channel_sidebar #groups"
        @data = {}
        @dom = {}
    
    # sets the initial data array
    init: (@data) ->
        @callback_init
        
    input: (data) -> 
        
    

