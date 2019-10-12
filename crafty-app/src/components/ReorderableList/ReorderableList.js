import React from 'react';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';

const reorder = (list, from, to) => {
  const result = Array.from(list);
  result.splice(to, 0, result.splice(from, 1)[0]);
  return result;
};

const onDrop = (items, setItems) => ({source, destination}) => {
  destination && setItems(reorder(items, source.index, destination.index));
};

const ReorderableList = ({ items, setItems, Item, direction='horizontal', className, disabled, extra }) => (
  <DragDropContext onDragEnd={onDrop(items, setItems)}>
    <Droppable droppableId="droppable" direction={direction}>
      {p => (
        <div ref={p.innerRef} className={className} style={{display: 'flex'}} {...p.droppableProps}>
          {items.map((item, index) => (
            <Draggable key={item.id} draggableId={item.id} index={index} isDragDisabled={disabled}>
              {(p, { isDragging }) => (
                <div ref={p.innerRef} {...p.draggableProps} {...p.dragHandleProps}>
                  <Item item={item} isDragging={isDragging} {...extra}/>
                </div>
              )}
            </Draggable>
          ))}
          {p.placeholder}
        </div>
      )}
    </Droppable>
  </DragDropContext>
);

export default ReorderableList;