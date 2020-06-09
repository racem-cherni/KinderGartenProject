package tn.esprit.spring.Service;

import tn.esprit.spring.entities.History;

public interface IHistoryService {
	public History addHistory(History h,Long userId ,Long postId);
	public void deleteReact(Long userId,Long postId);
	public History addHistoryPost(History h,Long userId);
}
