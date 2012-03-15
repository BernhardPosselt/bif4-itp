###
This class represents one channel
###

class Channel
    
  @updates = 0
  @id = -1
  @channels = $ '#channels ul'
  
  constructor: (@name) ->
  
  insert: (position) ->
  # insert and the element to the channel at a given position
  # position: the position where it should be added
    @dom_elem = $ '<li>'
    @channels.add @dom_elem
    $.data @dom_elem, 'id', @id
    
      
    


