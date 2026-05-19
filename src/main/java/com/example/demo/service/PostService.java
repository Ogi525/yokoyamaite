package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Post;
import com.example.demo.mapper.PostMapper;

@Service
public class PostService {

	private final PostMapper postMapper;
	//	private final ProductMapper productMapper;

	//ProductMapper連携したら消す
	public PostService(PostMapper postMapper) {
		this.postMapper = postMapper;
	}
	//ProductMapper追加後に使う
	//	public PostService(PostMapper postMapper,ProductMapper productMapper) {
	//		this.postMapper = postMapper;
	//		this.ProductMapper = productMapper;
	//	}

	public void addPost(Integer userId, Integer productId, String body, Integer parentId) {

		//productIdの確認
		//		if (productMapper.findById(productId) == null) {
		//			throw new IllegalArgumentException("存在しない商品です: " + productId);
		//		}

		// 不正なparentIdチェック（返信の場合のみ）
		if (parentId != null && postMapper.findById(parentId) == null) {
			throw new IllegalArgumentException("存在しないコメントです: " + parentId);
		}

		Post post = new Post();
		post.setUserId(userId);
		post.setProductId(productId);
		post.setBody(body);
		post.setParentId(parentId);

		postMapper.insertPost(post);
	}

	// ツリー構造で取得
	public List<Post> getTreePostsByProduct(Integer productId) {

		// 不正なproductIdチェック
		//		if (productMapper.findById(productId) == null) {
		//			throw new IllegalArgumentException("存在しない商品です: " + productId);
		//		}

		List<Post> parents = postMapper.findParentPostsByProduct(productId);

		for (Post parent : parents) {
			parent.setReplies(getRepliesRecursive(parent.getId(), new HashSet<>()));
		}

		return parents;
	}

	private List<Post> getRepliesRecursive(Integer parentId, Set<Integer> visited) {

		// 無限ループ防止
		if (visited.contains(parentId)) {
			return List.of();
		}

		visited.add(parentId);

		List<Post> replies = postMapper.findReplies(parentId);

		// null対策
		if (replies == null || replies.isEmpty()) {
			return List.of();
		}

		for (Post reply : replies) {
			reply.setReplies(
					getRepliesRecursive(reply.getId(), visited));
		}

		return replies;
	}

	public void deletePost(Integer postId, Integer userId) {

		Post post = postMapper.findById(postId);

		// 存在チェック
		if (post == null) {
			throw new IllegalArgumentException("存在しないコメントです: " + postId);
		}

		// 本人チェック
		if (!post.getUserId().equals(userId)) {
			throw new IllegalStateException("削除権限がありません");
		}

		// 返信ごと再帰削除
		deleteRecursive(postId);
	}

	private void deleteRecursive(Integer postId) {
		List<Post> replies = postMapper.findReplies(postId);

		for (Post reply : replies) {
			deleteRecursive(reply.getId()); // 孫コメントも再帰削除
		}

		postMapper.deleteReplies(postId); // 子を削除
		postMapper.deleteById(postId); // 自身を削除
	}
}