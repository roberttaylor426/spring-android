/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.facebook.api;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.social.test.client.RequestMatchers.body;
import static org.springframework.social.test.client.RequestMatchers.header;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.social.InsufficientPermissionException;
import org.springframework.social.NotAuthorizedException;

import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

public class LikeTemplateTest extends AbstractFacebookApiTest {

	@SmallTest
	public void testLike() {
		mockServer.expect(requestTo("https://graph.facebook.com/123456/likes"))
			.andExpect(method(POST))
			.andExpect(header("Authorization", "OAuth someAccessToken"))
			.andRespond(withResponse("{}", responseHeaders));
		facebook.likeOperations().like("123456");
		mockServer.verify();
	}
	
	@MediumTest
	public void testLike_objectAccessNotPermitted() {
		boolean success = false;
		try {
			mockServer.expect(requestTo("https://graph.facebook.com/123456/likes"))
				.andExpect(method(POST))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/error-permission"), responseHeaders, HttpStatus.FORBIDDEN, ""));
			facebook.likeOperations().like("123456");
		} catch (InsufficientPermissionException e) {
			success = true;
		}
		assertTrue("Expected InsufficientPermissionException", success);
	}
	
	@SmallTest
	public void testLike_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().like("123456");
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}

	@SmallTest
	public void testUnlike() {
		mockServer.expect(requestTo("https://graph.facebook.com/123456/likes"))
			.andExpect(method(POST))
			.andExpect(body("method=delete"))
			.andExpect(header("Authorization", "OAuth someAccessToken"))
			.andRespond(withResponse("{}", responseHeaders));
		facebook.likeOperations().unlike("123456");
		mockServer.verify();
	}

	@SmallTest
	public void testUnlike_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().unlike("123456");
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetLikes() {
		mockServer.expect(requestTo("https://graph.facebook.com/me/likes")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getLikes();
		assertLikes(likes);
	}

	@SmallTest
	public void testGetLikes_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getLikes();
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetLikes_forSpecificUser() {
		mockServer.expect(requestTo("https://graph.facebook.com/123456789/likes")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getLikes("123456789");
		assertLikes(likes);
	}
	
	@SmallTest
	public void testGetLikes_forSpecificUser_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getLikes("123456789");
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetBooks() {
		mockServer.expect(requestTo("https://graph.facebook.com/me/books")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getBooks();
		assertLikes(likes);
	}

	@SmallTest
	public void testGetBooks_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getBooks();
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetBooks_forSpecificUser() {
		mockServer.expect(requestTo("https://graph.facebook.com/123456789/books")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getBooks("123456789");
		assertLikes(likes);
	}	
	
	@SmallTest
	public void testGetBooks_forSpecificUser_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getBooks("123456789");
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetMovies() {
		mockServer.expect(requestTo("https://graph.facebook.com/me/movies")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getMovies();
		assertLikes(likes);
	}
	
	@SmallTest
	public void testGetMovies_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getMovies();
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}

	@MediumTest
	public void testGetMovies_forSpecificUser() {
		mockServer.expect(requestTo("https://graph.facebook.com/123456789/movies")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getMovies("123456789");
		assertLikes(likes);
	}
	
	@SmallTest
	public void testGetMovies_forSpecificUser_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getMovies("123456789");
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetMusic() {
		mockServer.expect(requestTo("https://graph.facebook.com/me/music")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getMusic();
		assertLikes(likes);
	}

	@SmallTest
	public void testGetMusic_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getMusic();
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetMusic_forSpecificUser() {
		mockServer.expect(requestTo("https://graph.facebook.com/123456789/music")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getMusic("123456789");
		assertLikes(likes);
	}

	@SmallTest
	public void testGetMusic_forSpecificUser_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getMusic("123456789");
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetTelevision() {
		mockServer.expect(requestTo("https://graph.facebook.com/me/television")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getTelevision();
		assertLikes(likes);
	}

	@SmallTest
	public void testGetTelevision_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getTelevision();
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetTelevision_forSpecificUser() {
		mockServer.expect(requestTo("https://graph.facebook.com/123456789/television")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getTelevision("123456789");
		assertLikes(likes);
	}
		
	@SmallTest
	public void testGetTelevision_forSpecificUser_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getTelevision("123456789");
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetActivities() {
		mockServer.expect(requestTo("https://graph.facebook.com/me/activities")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getActivities();
		assertLikes(likes);
	}

	@SmallTest
	public void testGetActivities_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getActivities();
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	@MediumTest
	public void testGetActivities_forSpecificUser() {
		mockServer.expect(requestTo("https://graph.facebook.com/123456789/activities")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getActivities("123456789");
		assertLikes(likes);
	}
	
	@SmallTest
	public void testGetActivities_forSpecificUser_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getActivities("123456789");
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}

	@MediumTest
	public void testGetInterests() {
		mockServer.expect(requestTo("https://graph.facebook.com/me/interests")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getInterests();
		assertLikes(likes);
	}

	@SmallTest
	public void testGetInterests_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getInterests();
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}

	@MediumTest
	public void testGetInterests_forSpecificUser() {
		mockServer.expect(requestTo("https://graph.facebook.com/123456789/interests")).andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withResponse(jsonResource("testdata/user-likes"), responseHeaders));
		List<UserLike> likes = facebook.likeOperations().getInterests("123456789");
		assertLikes(likes);
	}
	
	@SmallTest
	public void testGetInterests_forSpecificUser_unauthorized() {
		boolean success = false;
		try {
			unauthorizedFacebook.likeOperations().getInterests("123456789");
		} catch (NotAuthorizedException e) {
			success = true;
		}
		assertTrue("Expected NotAuthorizedException", success);
	}
	
	private void assertLikes(List<UserLike> likes) {
		assertEquals(3, likes.size());
		UserLike like1 = likes.get(0);
		assertEquals("113294925350820", like1.getId());
		assertEquals("Pirates of the Caribbean", like1.getName());
		assertEquals("Movie", like1.getCategory());
		UserLike like2 = likes.get(1);
		assertEquals("38073733123", like2.getId());
		assertEquals("Dublin Dr Pepper", like2.getName());
		assertEquals("Company", like2.getCategory());
		UserLike like3 = likes.get(2);
		assertEquals("10264922373", like3.getId());
		assertEquals("Freebirds World Burrito", like3.getName());
		assertEquals("Restaurant/cafe", like3.getCategory());
	}

}
