package xxxxxx.yyyyyy.zzzzzz.domain.service.errortest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MockTestServiceImpl implements MockTestService {

	@Override
	public void testExecute() {
	}
}
